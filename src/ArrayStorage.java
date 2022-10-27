import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int currentSize = 0;

    void clear() {
        for (int i = 0; i < currentSize; i++) {
            storage[i] = null;
        }
        currentSize = 0;
    }

    void save(Resume r) {
        if (currentSize == storage.length) {
            return;
        }
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                return;
            }
        }
        storage[currentSize] = r;
        currentSize++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int indexToDelete = 0;
        for (; indexToDelete < currentSize; indexToDelete++) {
            if (storage[indexToDelete].uuid.equals(uuid)) {
                break;
            }
        }
        if (indexToDelete == currentSize) {
            return;
        }
        currentSize--;
        storage[indexToDelete] = storage[currentSize];
        storage[currentSize] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, currentSize);
    }

    int size() {
        return currentSize;
    }
}
