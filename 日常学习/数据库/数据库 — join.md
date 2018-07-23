## 数据库 --- join
> SQL  join 用于根据两个或多个表中的列之间的关系，从这些表中查询数据。
#### Person表
| PersonId      |    FirstName| LastName|
| :-------- | --------:| :--: |
| 1| Jack|  Lee   |
|3|William|Li|
#### Address表
| AddressId | PersonId | City | State |
| :--------|-------:|:---:|:---:|
| 1| 1| ZhuHai | XiangZhou |
| 2| 2| GuangZhou | PanYu |
如上两个表，它们之间通过PersonId进行联系。
#### 引用两个表
我们可以通过引用两个表的方式来查询某个人在哪里
例：
```
select p.FirstName,p.LastName,a.City,a.State from Person p,Address a where p.PersonId = a.PersonId;
```
得到的结果：
| FirstName|    LastName| City|State|
| :-------- | --------:| :--: |
|Jack|  Lee |ZhuHai|XiangZhou|
这样，我们就可以得到Jack Lee的信息了。
#### 使用 join
下面列出可以使用的 JOIN 类型，以及它们之间的差异。
- JOIN: 如果表中有至少一个匹配，则返回行。也叫 inner join
- LEFT JOIN: 即使右表中没有匹配，也从左表返回所有的行。
- RIGHT JOIN: 即使左表中没有匹配，也从右表返回所有的行。
- FULL JOIN: 只要其中一个表中存在匹配，就返回行。
#### join
还是上面的两个表（后续都是）
```
select p.FirstName,p.LastName,a.City,a.State from Person p join on Address a on p.PersonId = a.PersonId;
```
得到的结果
| FirstName|    LastName| City|State|
| :-------- | --------:| :--: |
|Jack|  Lee |ZhuHai|XiangZhou |
此时，返回的只有Jack Lee的信息。因为在 Person 表和 Address 表中存在相同的 PersonId是 Jack Lee 的。
> join 关键字在表中存在至少一个匹配时返回行。如果 Person 表中的行在 Address 表中没有匹配，就不会列出这些行。
#### left join
```
select p.FirstName,p.LastName,a.City,a.State from Person p left join Address a on p.PersonId = a.PersonId;
```
得到的结果：
| FirstName|    LastName| City|State|
| :-------- | --------:| :--: |
|Jack|  Lee |ZhuHai|XiangZhou |
|William|Li|||
此时，表中的数据有 Jack Lee 和 William Li 两个人，但是 Jack Lee 的 City 和 State 是有信息的，而 William Li 的是空的。这是因为在 Address 表中不含 William Li 的信息(不存在匹配的 PersonId)。但是为什么会有返回呢？这是因为
> left join: 即使右表(Address)中没有匹配，也从左表(Person)返回所有的行。只不过没匹配的对应的属性值为空
#### right join
和 left join 差不多。
```
select p.FirstName,p.LastName,a.City,a.State from Person p right join Address a on p.PersonId = a.PersonId;
```
得到的结果：
| FirstName|    LastName| City|State|
| :-------- | --------:| :--: |
|Jack|  Lee |ZhuHai|XiangZhou |
|||GuangZhou |PanYu|
> right join 关键字会右表 (Address) 那里返回所有的行，即使在左表 (Person) 中没有匹配的行。
#### full join
就是 lfet join 和 right join 的结合体
```
select p.FirstName,p.LastName,a.City,a.State from Person p full join Address a on p.PersonId = a.PersonId;
```
得到的结果：
| FirstName|    LastName| City|State|
| :-------- | --------:| :--: |
|Jack|  Lee |ZhuHai|XiangZhou |
|William|Li|||
|||GuangZhou |PanYu|
> full  join 关键字会从左表 (Person) 和右表 (Address) 那里返回所有的行。如果 Person 中的行在表 Address 中没有匹配，或者如果 Address 中的行在表 Person 中没有匹配，这些行同样会列返回。
#### 练练手
[leetcode-175. 组合两个表](https://leetcode-cn.com/problems/combine-two-tables/description/)