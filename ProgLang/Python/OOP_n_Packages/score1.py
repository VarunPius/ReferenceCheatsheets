'''
On a file test2.py define the class ScoreList(), with an init function that stores an array as a class attribute named scores and an average method that returns the average of scores.

Create an instance of ScoreList and print the result of the average method.
'''
import random


class ScoreList():
    def __init__(self, int_array):
        self.int_array = int_array
        self.n = len(int_array)
    
    def get_avg(self):
        sum_array = 0

        for i in range(self.n):
            sum_array += self.int_array[i]
        
        print("Parent method")

        return sum_array/float(self.n)


def get_array(nos_of_int):
    int_array = []

    for _ in range(nos_of_int):
        int_array.append(random.randint(0, 1000))
    
    return int_array


'''
if __name__ == '__main__':
    nos_of_int = 10
    int_array = get_array(nos_of_int)
    print("Input:", int_array)
    soln = ScoreList(int_array)

    print(soln.get_avg())
'''