Git là một version controll system, được dùng nhằm quản lý, chia sẽ các dữ liệu, file, folder liên quan đến dự án.
Git có nhiều điểm mạnh như là: 
	- Theo dỏi lịch sử thay đổi của project, cho phép người dùng biết thời gian project được sửa đổi, chi tiết được
sửa đổi, và người sửa đổi.
	- Dễ dàng có được sự phối hợp giữa các thành viên trong nhóm. Git cho ta biết được những việc mà người trong
team đã thực hiện, dễ dàng kết hợp các chỉnh sửa, các phần của dự án. Và luôn có thể recovery lại data trong trường
hợp xấu như là delete file, mất data,...
	- Chia công việc ra thành nhiều nhánh (branch), dễ dàng cho việc thực thi công việc. Ví dụ project có nhiều
nhánh, nếu không có git thì sẽ không thể hoàn thành được nếu như có một nhánh nào đó chưa hoàn thành. Đối với git, 
nhánh hoàn thành trước có thể merg vào project mà không lo ngại về các nhánh khác.

Việc liên được theo dỏi lịch sử của project, người dùng hoàn toàn có thể không lo ngại trong nhưng trường hợp xấu
như là mất dữ liệu, dữ liệu bị làm sai, delete,... Git sẽ khôi phục lại project vào ngay thời điểm nó được commit.
Tuy nhiên, git sử dụng một hidden folder ở root của project vì thế dữ liệu được commit chỉ được lưu local. Trong những
trường hợp cực tệ như hư máy, máy vào nước,...thì dữ liệu dù đã commit vẫn sẽ mất. Để khắc phục tình trạng này, ta 
có thể sử dụng một kho lưu trữ remote như GitHub. Dữ liệu sau khi commit sẽ được đưa lên remote server và an toàn trong
hầu hết mọi trường hợp.
