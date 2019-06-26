class User:
    x = 1
    y = x + 100
    def __init__(self, name):
        self.name = name
        self.email = name + '@mail.com'


print("## By vars ##")
u1 = User('welson1')
u2 = User('welson2')
print(vars(u1))
print(vars(u2))

vars1 = vars(u1)
for key in vars1:
    print("{} => {}".format(key, getattr(u1, key)))


print("## By dir,callable,getattr ##")
print([attr for attr in dir(u1) if not callable(getattr(u1, attr)) and not attr.startswith("__")])

