# Jean Paul Espinosa
#  Dec 7, 2022
# Sudoku solver using recursion and backtracking

# HELPER FUNCTIONS
def print_sudoku(board):
    if len(board) != 9:
        print("ERROR, Invalid board passed to print_sudoku")
    for row in range(9):
        if len(board[row]) != 9:
            print("ERROR, Invalid board passed to print_sudoku")
        if row % 3 == 0 and row != 0:
            print("---------------------")
        line = ""
        for col in range(9):
            if col % 3 == 0 and col != 0:
                line = line + "| "
            if board[row][col] is None:
                line = line + "* "
            else:
                line = line + str(board[row][col]) + " "
        print(line)

def find_next_empty(puzzle):
    # finds the next row, col on puzzle that's not filled yet --> we represent these with -1
    # returns a row, col tuple (or (None, None) if there is none)

    # keep in mind that we are using 0-8 for our indices
    for r in range(9):
        for c in range(9):  # range(9) is 0, 1, 2,..., 8
            if puzzle[r][c] is None:
                return r, c

    return None, None   # if no spaces in the puzzle are empty (-1)


def is_valid(puzzle, guess, row, col):
    # figures out whether the guess at the row/col of the puzzle is a valid guess
    # returns True if is valid, False otherwise

    # starting with row
    row_values = puzzle[row]
    if guess in row_values:
        return False

    # now the column
        # col_values = []
        # for i in range(9):
        #   col_values.append(puzzle[i][col])
    col_values = [puzzle[i][col] for i in range(9)]  # this line replaces the previous 3 lines
    if guess in col_values:
        return False

    # now the square
    # need to find the starting index for the 3x3 matrix
    # and iterate over the values in the row/column
    row_start = (row // 3) * 3  # 1 // 3 = 0, 5 // 3 = 1, ...
    col_start = (col // 3) * 3

    for r in range(row_start, row_start + 3):
        for c in range(col_start, col_start + 3):
            if puzzle[r][c] == guess:
                return False

    # if we get here, these checks pass
    return True

############################################################
# SOLVER IMPLEMENTATION
############################################################


def solve_sudoku(puzzle):
    # solve sudoku using backtracking!
    # our puzzle is a list of lists, where each inner list is a row in our sudoku puzzle
    # return solution

    # step 1: choose somewhere on the puzzle to make a guess
    row, col = find_next_empty(puzzle)

    # step 1.1: if there's nowhere left, then we're done because we only allowed valid inputs
    if row is None:
        return True
    # step 2: if there is a place to put a number, then make a guess between 1 and 9
    for guess in range(1, 10):
        # step 3: check if this is a valid guess
        if is_valid(puzzle, guess, row, col):
            # step 3.1: if this is a valid guess, then place it at that spot on the puzzle
            puzzle[row][col] = guess
            # step 4: then we recursively call our solver!
            if solve_sudoku(puzzle):
                return True
        # step 5: if not valid or if nothing gets returned true, then we need to backtrack and try a new number
        puzzle[row][col] = None   # resets the guess

    # step 6: if none of the numbers that we try work, then this puzzle is UNSOLVABLE!!
    return False


if __name__ == '__main__':
    example_board = [
        [3, 9, -1,   -1, 5, -1,   -1, -1, -1],
        [-1, -1, -1,   2, -1, -1,   -1, -1, 5],
        [-1, -1, -1,   7, 1, 9,   -1, 8, -1],

        [-1, 5, -1,   -1, 6, 8,   -1, -1, -1],
        [2, -1, 6,   -1, -1, 3,   -1, -1, -1],
        [-1, -1, -1,   -1, -1, -1,   -1, -1, 4],

        [5, -1, -1,   -1, -1, -1,   -1, -1, -1],
        [6, 7, -1,   1, -1, 5,   -1, 4, -1],
        [1, -1, 9,   -1, -1, -1,   2, -1, -1]
    ]
    # print(solve_sudoku(example_board))
    # print(example_board)

    problem1 = [
        [None, None, 3, None, 2, None, 6, None, None],
        [9, None, None, 3, None, 5, None, None, 1],
        [None, None, 1, 8, None, 6, 4, None, None],

        [None, None, 8, 1, None, 2, 9, None, None],
        [7, None, None, None, None, None, None, None, 8],
        [None, None, 6, 7, None, 8, 2, None, None],

        [None, None, 2, 6, None, 9, 5, None, None],
        [8, None, None, 2, None, 3, None, None, 9],
        [None, None, 5, None, 1, None, 3, None, None]
    ]

    problem2 = [
        [None, 1, 3, 4, 2, None, None, 8, 6],
        [2, None, 4, 6, None, None, None, None, None],
        [None, 8, 7, None, 1, None, 3, None, None],

        [None, None, None, None, 3, None, 6, None, None],
        [None, 6, 2, 5, None, None, None, None, 3],
        [5, None, None, 7, 6, 4, None, 9, 1],

        [7, None, None, None, 4, None, 8, 1, None],
        [None, 4, None, 8, None, None, None, 6, None],
        [None, None, 1, 2, 5, 6, None, 3, 7]
    ]

    problem3 = [
        [6, None, None, None, None, 7, None, 2, None],
        [None, None, None, None, None, None, None, 1, 5],
        [2, 4, 9, None, 1, None, None, None, 3],

        [4, None, 5, 8, None, 1, 3, 9, None],
        [3, 8, None, None, 4, 9, None, None, None],
        [None, 1, 6, None, 7, None, None, None, None],

        [8, None, 4, 1, 5, 3, 6, None, 2],
        [None, None, None, None, 6, 4, 8, 3, None],
        [1, 6, None, None, None, 2, None, None, 9]
    ]

    print("\n\n Board #1\n")
    print_sudoku(problem1)
    print(solve_sudoku(problem1))
    print_sudoku(problem1)

    print("\n\n Board #2\n")
    print_sudoku(problem2)
    print(solve_sudoku(problem2))
    print_sudoku(problem2)

    print("\n\n Board #3\n")
    print_sudoku(problem3)
    print(solve_sudoku(problem3))
    print_sudoku(problem3)

    problem4 = [
        [None, 4, None, None, None, None, None, 8, None],
        [9, None, None, None, None, None, 6, None, 1],
        [3, None, None, 8, None, 7, None, None, 5],

        [None, None, 1, 9, None, 5, None, 6, None],
        [None, 2, None, None, None, None, None, 1, None],
        [None, 9, None, 1, None, 6, 8, None, None],

        [2, None, None, 5, None, 8, None, None, 6],
        [1, None, 4, None, None, None, None, None, 8],
        [None, 7, None, None, None, None, None, 9, None]
    ]
    print("\n\n Board #4\n")
    print_sudoku(problem4)
    print(solve_sudoku(problem4))
    print()
    print_sudoku(problem4)
