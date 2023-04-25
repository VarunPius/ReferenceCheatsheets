'''
On a file test3.py import the class ScoreList() defined in test2.py, Create an instance of ScoreList and print the result of the average method.
'''

import score1


if __name__ == '__main__':
    nos_of_int = 10
    int_array = score1.get_array(nos_of_int)
    print("Input:", int_array)
    soln = score1.ScoreList(int_array)

    print(soln.get_avg())

