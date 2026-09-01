
Processors, Linking & Memory Allocation 

---

1. Program Flow

Before a C++ program runs, it goes through several stages

```c++
Source Code  

↓  

Preprocessor  

↓  

Compiler  

↓  

Linker  

↓  

Executable Program  

↓  

Program Runs
```

Easy Explanation :

- Preprocessor prepares the code
- Compiler translates C++ into machine code
- Linker connects missing pieces together
- Program is created and can run

Exam Answer
- Compiler translates the source code into object code, while the linker connects object code and required implementations to create an executable program

Memory Trick
- Compiler = Translate
- Linker = Connect

---

2. Preprocessor

The preprocessor runs before compilation. It handles instructions beginning with `#` 

Examples:

```c++
#include <iostream>
#define SIZE 100
```

Easy Explanation
The preprocessor prepares the code before the compiler sees it

Exam Answer
A preprocessor is a program that runs before compilation and processes preprocessor directives such as #include  and #define

---

3. #include  Directive 

eg. `#include <iostream>` 

What happens?
The preprocessor copies the contents of the file into your program

Think of it as **Copy -> Paste**

Standard Library
```c++
#include <iostream>
#include <string>
```
- uses < >


Programmer Files
`#include "Student.h`
- uses " "


Exam Answer
`#include <filename>`  -> Standard library
`#include "filename"`  -> Programmer-created file


---

4. Header Files vs Implementation Files

- Student.h
```c++
class Student{
	public:
		void display();
};
```

Purpose : Contains declarations
Tells us : WHAT exists

- Student.cpp
```c++
void Student::display(){
	cout << "Hello" ;	
}
```

Purpose : Contains Implementations
Tells us : HOW it works

- main.cpp
```c++
#include "Student.h"

int main(){
	Student s;
	s.display();
}
```

Purpose : Uses the class

Easy Memory Trick
```
.h  
↓  
WHAT exists  

.cpp  
↓  
HOW it works
```

Exam Answer
Header files usually contains declarations, while implementations files contain the definition of functions and classes

---

5. Linking

Many programs use functions located in different files

Example
```
main.cpp
Student.cpp
```
- The linker connects them

Easy Explanation
The linker looks for the actual implementation of functions and joins all files together

Super Simple Explanation
```c++
Header File  
↓  
Function Exists  

  
CPP File  
↓  
Actual Function Code  

  
Linker  
↓  
Finds Function Code  
↓  
Connects Everything
```

Exam Answer
The linker combines object files and locates the implementations of functions and classes to create an executable program


---
Student Class Example

- Student.h
```c++
class Student{
	public:
		void display();
};
```

- Student.cpp
```c++
#include <iostream>
#include "Student.h"

using namespace std;

void Student::display(){
	cout << "Hello" << endl;
}
```

- main.cpp
```c++
#include "Student.h"

using namespace std;

int main(){
	Student s;
	s.display();
	
	return 0;

}
```

PASS DISCUSSION QUESTIONS

Where is display( ) declared?
- Student.h

Where is display( ) implemented?
- Student.cpp

Which files uses the class?
- main.cpp

What connects everything?
- The Linker

---

6. define

`#define SIZE 100`

Before Preprocessing
```
#define SIZE 10
int arr[SIZE];
```

After Preprocessing
`int arr[100]` 

Easy Explanation
The preprocessor replaces the text before compilation

Exam Answer
- `#define` creates symbolic constant by replacing text before compilation

---

7. Static Memory Allocation

Example
```
int age;
double balance;
```

Characteristics
- [x] Size known before runtime
- [x] Created automatically
- [x] Removed automatically

Exam Answer
Static memory allocation occurs before program execution and its size is known in advance

---

8. Dynamic Memory Allocation

`int* ptr = new int;`

Characteristics
- [x] Memory allocated during runtime
- [x] Size may not be known beforehand
- [x] Uses pointers

Exam Answer
Dynamic memory allocation occurs during runtime using the new operator

---

9. Stack vs Heap

Stack : Stores normal variables
```
int age;
double money;
```

Heap : Stores memory created using "new"
```
int* ptr = new int;
```

Exam Answer
The stack stores local variables, while the heap stores dynamically allocated memory

---

10. new Operator

`int* ptr = new int;` 

What new does
1. Allocates memory on heap
2. Return addres
3. Address stored in pointer

The new operator allocates memory on the heap and returns its address

---

11. delete Operator

`delete ptr;`

What delete Does : Returns heap memory back to the system

**GOLDEN RULE : Every new should have a delete**

Exam Answer
delete releases memory previously allocated with new


---

12. Memory Leak

`int* ptr = new int;`

NO : delete ptr;

Result
- Memory remains allocated
- Program gradually wastes memory

Exam Answer
A memory leak occurs when dynamically allocated memory is not release using delete

--- 

13. Dynamic Arrays

`int* ptr = new int[100]`  creates 100 integers on the heap

Common Exam Trap
- new int[100] means 100 integers
- new int(100) means 1 integer, value = 100

Exam Answer
Square brackets indicate dynamic array allocation

---

14. Deleting Arrays

Allocate
`int* ptr = new int[100];

Delete
`delete[] ptr;`

Rule
new int -> delete
new int[100] -> delete[ ]

Exam Answer
Arrays allocated with new[ ] must be released using delete[ ]

---

15. Dangling Pointer

```
int* ptr = new int(10);

delete ptr;

*ptr = 5;
```

Problem
Memory has already been deleted
Pointer still points to old address

Exam Answer
A dangling pointer points to memory that has already been released

---

16. Preventing Dangling Pointers

```
delete ptr;
ptr = NULL;
```

Modern C++
```
delete ptr;
ptr = nullptr;
```

Exam Answer
Setting a pointer to NULL or nullptr after deletion helps prevent dangling pointers


---

PASS SESSION CLOSING SUMMARY

```
Preprocessor  
↓  
Handles #include and #define  

  
Compiler  
↓  
Translates source code  

  
Linker  
↓  
Finds implementations and connects files  

  
Stack  
↓  
Normal variables  


Heap  
↓  
Dynamic memory  

  
new  
↓  
Allocates memory  

  
delete  
↓  
Releases memory  

  
new[]  
↓  
delete[]  

  
Missing delete  
↓  
Memory Leak  

  
Use after delete  
↓  
Dangling Pointer
```

Header files tells us WHAT exists, implementation files show HOW it works, and the linker finds the actual code and connects everything together

