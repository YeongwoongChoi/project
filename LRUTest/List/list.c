#include "list.h"
#include <stdio.h>
#include <malloc.h>

extern int is_empty(List*);

List* create_list() {
	List* l = (List*)calloc(1, sizeof(List));
	if (!l) {
		fprintf(stderr, "List allocation failed.\n");
		return NULL;
	}
	return l;
}

void destruct_list(List* l, Destructor d) {
	if (!l)
		return;

	Node* curr = l->head;
	while (curr) {
		Node* next = curr->next;
		if (d)
			d(curr->value);
		free(curr);
		curr = next;
	}
	free(l);
}

Node* push_front(List* l, void* value) {
	if (!l)
		return NULL;

	Node* created = (Node*)calloc(1, sizeof(Node));
	if (!created) {
		fprintf(stderr, "Couldn't create new node.\n");
		return NULL;
	}

	created->value = value;

	if (is_empty(l))
		l->tail = created;
	else {
		created->next = l->head;
		l->head->prev = created;
	}
	l->head = created;
	l->size++;
	return created;
}

Node* push_back(List* l, void* value) {
	if (!l)
		return NULL;

	Node* created = (Node*)calloc(1, sizeof(Node));
	if (!created) {
		fprintf(stderr, "Couldn't create new node.\n");
		return NULL;
	}

	created->value = value;

	if (is_empty(l))
		l->head = created;
	else {
		created->prev = l->tail;
		l->tail->next = created;
	}
	l->tail = created;
	l->size++;
	return created;
}

void* pop_front(List* l) {
	if (!l || is_empty(l))
		return NULL;

	Node* target = l->head;
	void* value = target->value;

	l->head = target->next;
	if (l->head)
		l->head->prev = NULL;
	else
		l->tail = NULL;
	l->size--;
	free(target);

	return value;
}

void* pop_back(List* l) {
	if (!l || is_empty(l))
		return NULL;

	Node* target = l->tail;
	void* value = target->value;

	l->tail = target->prev;
	if (l->tail)
		l->tail->next = NULL;
	else
		l->head = NULL;
	l->size--;
	free(target);

	return value;
}

void* get_first(List* l) {
	if (!l || is_empty(l))
		return NULL;
	return l->head->value;
}

void* get_last(List* l) {
	if (!l || is_empty(l))
		return NULL;
	return l->tail->value;
}

static void _unlink(List* l, Node* target) {
	if (target->prev)
        target->prev->next = target->next;
    else
        l->head = target->next;

    if (target->next)
        target->next->prev = target->prev;
    else
        l->tail = target->prev;
    l->size--;	
}

void* remove_node(List* l, Node* target) {
	if (!l || !target)
		return NULL;

	void* value = target->value;
	_unlink(l, target);
	free(target);
	return value;
}

void detach_node(List* l, Node* target) {
	if (!l || !target)
		return;
	_unlink(l, target);
}

void push_node(List* l, Node* target) {
	if (!l || !target)
		return;
	target->next = l->head;
	target->prev = NULL;

	if (!l->head)
		l->tail = target;
	else
		l->head->prev = target;

	l->head = target;
	l->size++;
}
