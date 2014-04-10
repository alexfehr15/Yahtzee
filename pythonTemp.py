def merge(l1, l2):
	"""
	if len(l1) > len(l2):
		L1 = l1
		L2 = l2
	elif len(l2) > len(l1):
		L1 = l2
		L2 = l1
	else:
		L1 = l1
		L2 = l2
	L1 = sorted(L1)
	L2 = sorted(L2)
	"""
	L3 = L1 + L2
	L3 = sorted(L3)
	for i in L3:
		yield i

L1 = [4, 5, 3, 6, 7]
L2 = [1, 9, 10, 2, 12, 15]
for x in merge(L1, L2):
	print(x)
