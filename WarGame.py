import random

# 1. (3pts) Create a Deck class that has:
# A constructor that creates an instance variable named cards that holds a list of cards
# To help with shuffling and creation, consider using numbers to represent each of the cards, and a dictionary to map integers to specific Card objects
# A method that draws a card from the deck (returns a card)
# A method that returns whether or not the deck is empty
# A method that shuffles the deck
# A method that resets the deck (creates a new list of cards and shuffles them)

class Deck:

    def __init__(self):
        self.cards = []
        self.dct = {}
        self.reset()

    def reset(self):
        self.cards = list(range(1, 53))
        self.shuffle()
        i = 1
        while  i < 53:
            for suit in ["Clubs","Diamonds","Hearts","Spades"]:
                for rank in range(2, 15):
                    self.dct[i] = Card(rank, suit, rank, i)
                    i += 1

    def draw(self):
        if self.isEmpty():
            self.reset()
        else:
            a = self.dct[self.cards[0]].value
            print("{} of {}".format(self.dct[self.cards[0]].rank, self.dct[self.cards[0]].suit))
            self.cards.pop(0)
            return a

    def isEmpty(self):
        if not self.cards:
            return True
        return False

    def shuffle(self):
        random.shuffle(self.cards)

# 2. (2pts) Create a Card class with
# a variable for the rank (2,3,4,5,6,7,8,9,10,J,Q,K, A)
# a variable for the suit (clubs, diamonds, hearts, spades)
# a value (suits are irrelevant in this game). Four our game it will be:
# a key number: each Card object will have a unique number associated with it

class Card:
    def __init__(self, rank, suit, value, key):
        if rank == 11:
            self.rank = "Jack"
        elif rank == 12:
            self.rank = "Queen"
        elif rank == 13:
            self.rank = "King"
        elif rank == 14:
            self.rank = "Ace"
        else:
            self.rank = rank
        self.suit = suit
        self.value = value
        self.key = key

# 3. (5pts) Play the game as before, but this time the scores will be sums of values, not card counts. Have it go 5 rounds before ending, declaring which player won, or if it's a tie, that there is a tie.  Use these classes to play the game (-5pts if you don't)!
# This time, however, print out which cards are drawn by each player for each round, rank and suit, and which player won that particular round (or that there is a tie, if neither win). -2pts if you don't do this!

deck = Deck()

p1Score = 0
p2Score = 0
round = 1

while round < 6:
    print("\nPlayer 1 plays a", end = " ")
    a = deck.draw()
    print("Player 2 plays a", end = " ")
    b = deck.draw()
    if a > b:
        print("Player 1 Wins!")
        p1Score = p1Score + a + b
    elif a < b:
        print("Player 2 Wins!")
        p2Score = p2Score + a + b
    else:
        print("Tie Round!")
        p1Score = p1Score
        p2Score = p2Score
    print ("Player 1 Score: " + str(p1Score))
    print ("Player 2 Score: " + str(p2Score))
    print("\n")
    round += 1

print("Game Over!\nFinal Score:")
print ("Player 1 Score: " + str(p1Score))
print ("Player 2 Score: " + str(p2Score))
if p1Score > p2Score:
    print ("Player 1 Wins!")
elif p1Score < p2Score:
    print ("Player 2 Wins!")
else:
    print("The Game is a Tie!")
