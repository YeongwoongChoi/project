typedef struct Entry {
	char* key;
	void* value;
	struct Entry* next;
} Entry;

typedef struct {
	int size;
	int count;
	Entry** buckets;
} HashTable;

typedef void (*Destructor)(void*);

unsigned long hash(char* key);
HashTable* init_table(int init_capacity);
void destruct_table(HashTable* t, Destructor d);

/// <summary>
/// Insert designated key and value in HashTable
/// </summary>
/// <returns>
/// Newly inserted Entry pointer if key exists else nullptr
/// </returns>
Entry* put(HashTable* t, char* key, void* value);

/// <summary>
/// Find value mapped into designated key in HashTable
/// </summary>
/// <param name="t">
/// HashTable to search key
/// </param>
/// <param name="key">
/// Key to find value
/// </param>
/// <returns>
/// Value if exists else nullptr
/// </returns>
void* get(HashTable* t, char* key);

/// <summary>
/// Remove value mapped into designated key in HashTable
/// </summary>
/// <param name="t">
/// HashTable to find value
/// </param>
/// <param name="key">
/// Key to remove value
/// </param>
/// <returns>
/// Mapped value if exists else nullptr
/// </returns>
void* remove_key(HashTable* t, char* key);

inline int need_rehash(HashTable* t) {
	return t->count * 4 >= t->size * 3;
}

void rehash(HashTable* t);