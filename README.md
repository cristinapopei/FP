# FP
Projects for Functional Programming in Scala, Semester 2, FILS, UNSTPB, 2023-2024

Homework 1: Sets as Functions
Problem Statement

Sets are unordered collections of unique elements, often represented by characteristic functions. In this implementation, we represent sets of integers using characteristic functions of type Int => Boolean.

    singleton: Returns a set containing only the given integer.
    member: Checks if an integer is a member of a given set.
    ins: Inserts a new element into a set.
    fromBounds: Creates a set containing integers within a specified range.
    union: Computes the union of two sets.
    complement: Computes the complement of a set with respect to the set of integers.
    sumSet: Computes the sum of elements within a set that fall within a given interval.
    foldLeftSet: Folds a set using a left fold operation.
    foldRightSet: Folds a set using a right fold operation.
    filter: Filters elements of a set based on a predicate.
    partition: Partitions a set into two sets based on a predicate.
    forall: Checks if all elements in a given range from a set satisfy a predicate.
    exists: Checks if a predicate holds for some element from the range of a set.
    setOfDivByK: Returns the set of integers divisible by a given value.
    moreDivs: Verifies if one set contains more divisors of a value than another set over a specified range.

Implementation

The implementation of these functions involves defining operations on sets represented as characteristic functions.
Usage

These functions can be utilized for various set operations and predicates evaluation over sets of integers.

For detailed implementation and usage instructions, refer to the source code comments.


Homework 2: Sets as Trees
Problem Statement

This assignment involves implementing a binary search tree (BST) named WTree to gather statistics about words from a text. Each node in the BST will contain a Token object, storing the word and its frequency in the text. The Token class is provided as follows:
case class Token(word: String, freq: Int)

The tasks include constructing the WTree from a text and using it to gather information about the text.
Implementation

    Splitting Text: Implement a function to split a text into words using a single whitespace as a separator.

    Compute Tokens: Write a function to compute a list of Token objects from a list of strings. Create an auxiliary function to insert strings into the list of tokens.

    Tokens to Tree: Write a function to convert a list of tokens into a WTree using the insertion function.

    Make Tree: Develop a function to build a WTree from a string, utilizing the previously implemented functions.

    Size: Implement the size method to return the number of non-empty nodes in the tree.

    Contains: Implement the contains method to check if a string is a member of the tree.

    Filter: Implement the filter method to filter nodes in the tree based on a predicate.

    Scala Frequency: Compute the number of occurrences of the keyword "Scala" in a given string using word-trees and the implemented functions.

    Programming Languages: Find how many programming languages are referenced in the text, considering any keyword starting with an uppercase character.

    Word Count: Find how many words, excluding prepositions or conjunctions (words with size less than or equal to 3), appear in the text.

Using andThen

The andThen function can simplify applying a sequence of transformations over an object, making the code more readable and easier to debug.
Usage

Follow the provided instructions and function signatures to implement each task. Ensure proper testing to validate the correctness of your implementation.


Homework 3: 5-in-a-Row
About 5-in-a-Row

The 5-in-a-Row game:

    Can be played on a square board of any size equal to or larger than 5.
    A player wins if they have marked a line, column, or diagonal of 5 consecutive positions in a row.

Coding Conventions

    X is encoded as the first player (One), and 0 as Two in the project template.
    A Board is represented as a List of Lists of positions (a matrix), where a position can be One, Two, or Empty. The distinction between position and player is not made in the code, making it slightly easier to write.

Coding Tasks

    Board Creation: Implement a function to convert a string into a Board, considering the encoding conventions. Use the apply method in the companion object Board.

    Check Free Position: Implement the isFree function to check if a position (x, y) on the board is free.

    Opponent Identification: Write a function to return the opponent of a player.

    Board to String: Implement the toString function to convert a board to a string, adhering to the encoding strategy.

    Columns Extraction: Write a function to return the columns of a board.

    Diagonal Extraction: Implement functions to extract the first and second diagonals from a board.

    Additional Diagonals Extraction: Implement functions to extract diagonals above/below the first/second diagonal.

    Check Winner: Write a function to check if the current player is the winner.

    Update Position: Implement a function to update a position on the board with a given player.

    Generate Next Moves: Write a function to generate all possible next moves for any of the two players.

Usage

Follow the provided instructions and function signatures to implement each task. Ensure thorough testing to validate the correctness of your implementation.
