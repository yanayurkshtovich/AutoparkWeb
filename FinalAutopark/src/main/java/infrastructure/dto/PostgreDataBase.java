package infrastructure.dto;

import entities.Vehicles;
import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.core.impl.Context;
import infrastructure.dto.annotations.Column;
import infrastructure.dto.annotations.ID;
import infrastructure.dto.annotations.Table;
import infrastructure.dto.enums.SqlFieldType;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class PostgreDataBase {
    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String,String> classToSql;
    private Map<String,String> insertPatternByClass;
    @Autowired
    private Context context;
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN = "SELECT EXISTS (\n" +
            "SELECT FROM information_schema.sequences  \n" +
            "WHERE sequence_schema = 'public'\n" +
            "AND sequence_name = '%s'\n" +
            ");";
    private static final String CREATE_ID_SEQ_PATTERN = "CREATE SEQUENCE IF NOT EXISTS %S\n" +
            "INCREMENT 1\n" +
            "START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN = "SELECT EXISTS (\n" +
            "SELECT FROM information_schema.tables \n" +
            "WHERE table_schema = 'public'\n" +
            "AND table_name = '%s'\n" +
            ");";
    private static final String CREATE_TABLE_SQL_PATTERN = "CREATE TABLE %s (\n" +
            "%s integer PRIMARY KEY DEFAULT nextval('%s')" +
            "%S\n);";
    private static final String INSERT_SQL_PATTERN = "INSERT INTO %s(%s)\n" +
            "VALUES (%s)\n" +
            "RETURNING %s;";
    private Map<String,String> insertByClassPattern;

    public PostgreDataBase() {}

    @InitMethod
    public void init() {
        initializeClassToSql();
        initializeInsertPatternByClass();

        if (!isSequenceExists()) {
            createSequence();
        }

        Set<Class<?>> entities = context.getConfig().getScanner().getSubTypesOf(Object.class)
                .stream()
                .filter(x -> x.isAnnotationPresent(Table.class)).filter(this::validateEntity)
                .collect(Collectors.toSet());
        for (Class<?> clazz : entities) {
            if (!isTableExists(clazz)) {
                createTable(clazz);
            }
        }

        this.insertByClassPattern = entities.stream().collect(Collectors.toMap(Class::getName, this::buildInsertRequest));
    }

    public void deleteRowByID(Long id, String tableName) {
        StringBuilder sqlDeleteRowQueryBuilder = new StringBuilder("DELETE FROM ");
        sqlDeleteRowQueryBuilder.append(tableName).append(" WHERE id = ").append(id);
        try (Statement st = connectionFactory.getConnection().createStatement()) {
            st.executeUpdate(sqlDeleteRowQueryBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long save(Object obj) {
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), createValuesArray(obj));
        try (Statement statement = connectionFactory.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            String idFieldName = null;
            for (Field f : obj.getClass().getDeclaredFields()) {
                idFieldName = f.getAnnotation(ID.class).name();
                if (idFieldName != null) {
                    f.setAccessible(true);
                    while(rs.next()) {
                        f.set(obj, rs.getLong(idFieldName));
                    }
                    break;
                }
                while (rs.next()) {
                    return rs.getLong(idFieldName);
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return 0L;
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        Optional<T> optional = Optional.empty();
        try {
            isClassTable(clazz);
            String sql = "SELECT * FROM " +
                    clazz.getAnnotation(Table.class).name() +
                    " WHERE " + Arrays.stream(Vehicles.class.getDeclaredFields())
                    .filter(x -> x.isAnnotationPresent(ID.class))
                    .map(x -> x.getAnnotation(ID.class).name()).findFirst()
                    .orElse(null)+ " = " + id;
            connection = connectionFactory.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                optional = Optional.of(makeObject(rs, clazz));
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {System.out.println(e.getMessage());};
            try { if (st != null) st.close(); } catch (Exception e) {System.out.println(e.getMessage());};
            try { if (connection != null) connection.close(); } catch (Exception e) {System.out.println(e.getMessage());}
        }

        return optional;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<T> allTableObjects = new ArrayList<>();
        try {
            isClassTable(clazz);
            String sql = "Select * FROM " + clazz.getAnnotation(Table.class).name();;
            connection = connectionFactory.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                allTableObjects.add(makeObject(rs, clazz));
            }
        } catch (Exception e) {System.out.println(Arrays.toString(e.getStackTrace()));}
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {System.out.println(e.getMessage());};
            try { if (st != null) st.close(); } catch (Exception e) {System.out.println(e.getMessage());};
            try { if (connection != null) connection.close(); } catch (Exception e) {System.out.println(e.getMessage());}
        }

        return allTableObjects;
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet rs, Class<T> clazz) {
        T objectFromDB = clazz.getConstructor().newInstance();
        Object value = null;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(ID.class)) {
                value = rs.getObject(f.getAnnotation(ID.class).name());
            }
            else if (f.isAnnotationPresent(Column.class)) {
                value = rs.getObject(f.getAnnotation(Column.class).name());
            }
            f.setAccessible(true);
            if (value != null) {
                f.set(objectFromDB, f.getType().getConstructor(String.class).newInstance(value.toString()));
            }
//            else {
//                f.set(objectFromDB, f.getType().getConstructor(String.class).newInstance("-1"));
//            }
        }

        return objectFromDB;
    }

    private <T> boolean isClassTable(Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new Exception("The class is not table annotated.");
        }
        else {
            return true;
        }
    }

    private Object[] createValuesArray(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(x -> x.isAnnotationPresent(Column.class)).toArray();
    }

    private void initializeClassToSql() {
        this.classToSql = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(x -> x.getType().getName(), SqlFieldType::getSqlType));
    }

    private void initializeInsertPatternByClass() {
        this.insertPatternByClass = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(x -> x.getType().getName(), SqlFieldType::getInsertPattern));
    }

    private void createTable(Class<?> clazz) {
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            statement.execute(buildCreateTableRequest(clazz));
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private String buildInsertRequest(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idFieldName = clazz.getAnnotation(ID.class).name();
        StringBuilder insertFieldsSb = new StringBuilder();
        StringBuilder valuesSb = new StringBuilder();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                insertFieldsSb.append(f.getAnnotation(Column.class).name()).append(",");
                valuesSb.append(insertPatternByClass.get(f.getType().getName())).append(",");
            }
        }
        insertFieldsSb.deleteCharAt(insertFieldsSb.length() - 1);
        valuesSb.deleteCharAt(valuesSb.length() - 1);
        return String.format(INSERT_SQL_PATTERN, tableName, insertFieldsSb, valuesSb, idFieldName);
    }

    private String buildCreateTableRequest(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idField = clazz.getAnnotation(ID.class).name();
        StringBuilder sb = new StringBuilder();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                sb.append(f.getAnnotation(Column.class).name()).append(" ");
                sb.append(classToSql.get(f.getType().getName())).append(" ");
                if (f.getAnnotation(Column.class).nullable()) {
                    sb.append(" NOT NULL");
                }
                if (f.getAnnotation(Column.class).unique()) {
                    sb.append(" UNIQUE");
                }
                sb.append(",");
            }
        }
        return String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, sb);
    }

    private boolean isSequenceExists() {
        boolean sequenceExistanceFlag = false;
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                sequenceExistanceFlag = rs.getBoolean("exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sequenceExistanceFlag;
    }

    private void createSequence() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateEntity(Class<?> clazz) {
        try {
            IDFieldValidation(clazz);
            ColumnFieldValidation(clazz);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return true;
    }

    private void IDFieldValidation(Class<?> clazz) throws Exception {
        boolean IDfield = false;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(ID.class) && f.getType().equals(Long.class)) {
                IDfield = true;
                break;
            }
        }
        if (!IDfield) {
            throw new Exception("ID annotation is absent");
        }
    }

    private void ColumnFieldValidation(Class<?> clazz) throws Exception {
        Set<String> uniqueColumnNames = new HashSet<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                if (!uniqueColumnNames.contains(f.getAnnotation(Column.class).name())
                        && !f.getType().isPrimitive()) {
                    uniqueColumnNames.add(f.getAnnotation(Column.class).name());
                }
                else {
                    throw new Exception("Column name is not unique or primitive type");
                }
            }
        }
    }

    private boolean isTableExists(Class<?> clazz){
        boolean isTableExistsFlag = false;
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, clazz.getAnnotation(Table.class).name());
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                isTableExistsFlag = rs.getBoolean("exists");
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return isTableExistsFlag;
    }
}
