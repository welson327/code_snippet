
class Constant:
	APP_NAME = "test_module"
	APP_DESC = "test module to understand python module"


class User:
	def __init__(self, name):
		self.name = name


class Util:
	@staticmethod
	def print_user(user):
		print("User: ")
		print("  name: " + user.name)