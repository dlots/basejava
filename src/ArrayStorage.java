/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int _size = 0;

    void clear() {
        for (int i = 0; i < _size; ++i) {
            storage[i] = null;
        }
        _size = 0;
    }

    void save(Resume r) {
        if (_size == storage.length) {
            return;
        }
        for (int i = 0; i < _size; ++i) {
            if (storage[i].uuid.equals(r.uuid)) {
                return;
            }
        }
        storage[_size] = r;
        _size += 1;
    }

    Resume get(String uuid) {
        for (int i = 0; i < _size; ++i) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexToDelete = 0;
        for (; indexToDelete < _size; ++indexToDelete) {
            if (storage[indexToDelete].uuid.equals(uuid)) {
                break;
            }
        }
        if (indexToDelete == _size) {
            return;
        }
        for (int currentIndex = indexToDelete; currentIndex < _size - 1; ++currentIndex) {
            storage[currentIndex] = storage[currentIndex + 1];
        }
        _size -= 1;
        storage[_size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[_size];
        for (int i = 0; i < _size; ++i) {
            if (storage[i] == null) {
                break;
            }
            all[i] = storage[i];
        }
        return all;
    }

    int size() {
        return _size;
    }
}
