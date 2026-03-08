#include "hash.h"
#include <malloc.h>
#include <stdio.h>
#include <string.h>

extern int need_rehash(HashTable*);

unsigned long hash(char* key) {
	unsigned long val = 5381;
	int c;
	while ((c = *key++))
		val = ((val << 5) + val) + c;
	return val;
}

HashTable* init_table(int init_capacity) {
	HashTable* t = (HashTable*)calloc(1, sizeof(HashTable));
	if (!t) {
		fprintf(stderr, "Couldn't create table.\n");
		return NULL;
	}

	t->buckets = (Entry**)calloc(init_capacity, sizeof(Entry*));
	if (!t->buckets) {
		free(t);
		fprintf(stderr, "Couldn't create buckets.\n");
		return NULL;
	}
	t->size = init_capacity;
	t->count = 0;
	return t;
}

void destruct_table(HashTable* t, Destructor d) {
	if (!t)
		return;

	for (int i = 0; i < t->size; ++i) {
		Entry* curr = t->buckets[i];
		while (curr) {
			Entry* next = curr->next;

			if (d)
				d(curr->value);
			free(curr->key);
			free(curr);
			curr = next;
		}
	}
	free(t->buckets);
	free(t);
}

Entry* put(HashTable* t, char* key, void* value) {
	if (!t || !key)
		return NULL;
	
	if (need_rehash(t))
		rehash(t);

	unsigned long hash_index = hash(key) % t->size;
	Entry* e = t->buckets[hash_index];
	while (e) {
		if (!strcmp(e->key, key)) {
			e->value = value;
			return e;
		}
		e = e->next;
	}

	e = (Entry*)calloc(1, sizeof(Entry));
	if (!e)
		return NULL;

	e->key = strdup(key);
	e->value = value;
	e->next = t->buckets[hash_index];
	t->buckets[hash_index] = e;
	t->count++;
	return e;
}

void* get(HashTable* t, char* key) {
	if (!t || !key)
		return NULL;
	unsigned long hash_index = hash(key) % t->size;
	Entry* e = t->buckets[hash_index];
	while (e) {
		if (!strcmp(e->key, key))
			return e->value;
		e = e->next;
	}
	return NULL;
}

void* remove_key(HashTable* t, char* key) {
	if (!t || !key)
		return NULL;
	unsigned long hash_index = hash(key) % t->size;
	Entry* e = t->buckets[hash_index];
	Entry* prev = NULL;

	while (e) {
		if (!strcmp(e->key, key)) {
			if (!prev)
				t->buckets[hash_index] = e->next;
			else
				prev->next = e->next;

			void* value = e->value;
			free(e->key);
			free(e);
			t->count--;
			return value;
		}
		prev = e;
		e = e->next;
	}
	return NULL;
}

void rehash(HashTable* t) {
	int old_size = t->size;
	int new_size = old_size * 2;
	Entry** old_buckets = t->buckets;

	Entry** new_buckets = (Entry**)calloc(new_size, sizeof(Entry*));
	if (!new_buckets)
		return;

	for (int i = 0; i < old_size; ++i) {
		Entry* curr = t->buckets[i];
		while (curr) {
			Entry* next = curr->next;
			unsigned long hash_index = hash(curr->key) % new_size;
			curr->next = new_buckets[hash_index];
			new_buckets[hash_index] = curr;
			curr = next;
		}
	}
	t->buckets = new_buckets;
	t->size = new_size;
	free(old_buckets);
}
