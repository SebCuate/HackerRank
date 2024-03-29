/**
 * 
 * https://www.hackerrank.com/challenges/symmetric-pairs/problem
 * 
 * You are given a table, Functions, containing two columns: X and Y.
 * x - Integer
 * y - integer 
 * 
 * 
 * Two pairs (X1, Y1) and (X2, Y2) are said to be symmetric pairs if X1 = Y2 and X2 = Y1.
 * Write a query to output all such symmetric pairs in ascending order by the value of X. 
 * List the rows such that X1 <= Y1.
 * 
 */
/*Solucion 1*/
WITH 
    Functions_help as(
    select
        ROW_NUMBER() OVER () AS id,
        x, y 
    from Functions f1
    order by id
)
select 
	distinct /*Solucion 1*/
    f1.x, f1.y
from Functions_help f1
join Functions_help f2
    on f1.x = f2.y and f1.y = f2.x and f1.id != f2.id
WHERE f1.X <= f1.Y
group by f1.x, f1.y /*solucion 2*/
order by f1.x

/*DATA*/
/*
86 86
27 27
45 45
95 95
11 11
18 8
85 85
2 2
77 77
91 91
15 15
84 84
51 51
32 32
35 35
8 8
92 92
67 67
62 62
33 33
13 13
15 11
18 18
3 3
38 38
80 80
34 34
6 6
72 72
14 12
44 44
4 22
90 90
47 47
78 78
23 3
42 42
56 56
79 79
55 55
65 65
17 17
64 64
4 4
28 28
19 19
17 9
36 36
25 25
81 81
60 60
48 48
5 5
88 88
7 19
21 21
29 29
52 52
9 17
9 9
13 13
16 10
1 1
31 31
46 46
7 7
58 58
23 23
87 87
83 83
66 66
93 93
24 2
98 98
53 53
20 6
61 61
20 20
96 96
99 99
73 73
2 24
14 14
71 71
5 21
22 4
75 75
6 20
97 97
41 41
26 26
22 22
8 18
74 74
40 40
21 5
94 94
76 76
49 49
11 15
59 59
89 89
68 68
24 24
37 37
12 12
63 63
43 43
16 16
100 100
39 39
25 1
69 69
54 54
50 50
30 30
10 10
*/