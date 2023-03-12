import java.util.List;

public interface CityDAO {
    void create(City city);

    City readById(int id);

    List<City> readAll();

    void update(City city);

    void delete(City city);
}
