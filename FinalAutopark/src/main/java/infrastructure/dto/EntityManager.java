package infrastructure.dto;

import java.util.List;
import java.util.Optional;

public interface EntityManager {
    <T> Optional<T> get(Long id, Class<T> clazz);
    Long save(Object object);
    <T> List<T> getAll(Class<T> clazz);
    void deleteRowByID(Long id, String tableName);
}
