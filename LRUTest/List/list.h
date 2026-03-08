typedef struct Node {
	void* value;
	struct Node* prev, * next;
} Node;

typedef struct {
	Node* head, * tail;
	int size;
} List;

typedef void (*Destructor)(void*);

inline int is_empty(List* l) {
	return (l->size == 0 || !l->head);
}

List* create_list();
void destruct_list(List* l, Destructor d);
void* pop_front(List* l);
void* pop_back(List* l);
Node* push_front(List* l, void* value);
Node* push_back(List* l, void* value);
void detach_node(List* l, Node* n);
void* remove_node(List* l, Node* n);
void push_node(List* l, Node* n);
void* get_first(List* l);
void* get_last(List* l);
