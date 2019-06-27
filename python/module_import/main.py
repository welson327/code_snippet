# Import a file
from src import combo
print(combo.Constant.APP_NAME)


# Import a class from a file
from src.combo import Constant
print(Constant.APP_DESC)


# Import all from a file
from src.combo import *
u = User('welson')
Util.print_user(u)


# Import a module from package
import src.say as SrcSay
print(SrcSay.hello())


# Import a module by sugarly syntax
import sys
sys.path.append("src")
import say
print(say.hello())


