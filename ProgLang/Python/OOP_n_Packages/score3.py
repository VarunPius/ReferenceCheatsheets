'''
On a file test4.py import the class ScoreList() defined in test2.py, 
    define a class ScoreListImproved, child of ScoreList, that implements a method for median calculation (ask for statistics library).

Create an instance of ScoreListImproved and print the result of the average and median methods.
'''

import score1

import random
from statistics import median, mean


class ScoreListImproved(score1.ScoreList):
    def __init__(self, int_array):
        super().__init__(int_array)     # No need if child class has no properties of it's own like here. Simply add pass
        
    
    def get_median(self):
        med = median(self.int_array)

        return med
    
    def get_avg(self):
        #old_val = super().get_avg()        # We call Parent's get_avg() method this way, if you have to inherit anything
                                
        old_val = score1.ScoreList.get_avg(self)	# Alternative method
        avg = mean(int_array)
        return avg, old_val


if __name__ == '__main__':
    nos_of_int = 10
    int_array = score1.get_array(nos_of_int)
    print("Input:", int_array)

    soln = ScoreListImproved(int_array)

    print(soln.get_avg())



