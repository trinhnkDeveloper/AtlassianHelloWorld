Confluence Plugin sử dụng Velocity template, một trang server-side để thể hiện các trang HTML cùng java object.
Velocity tách java code ra khỏi một trang web từ đó khiến cho trang web dễ dàng được xây dụng và sửa chửa.
Việc tạo một trang html thể hiện cho một trang web được thực hiện bằng một file có tên mở rộng là .vm 
với cấu trúc như một trang html bình thường.

Một Acction class sẽ được gọi khi có sự chuyển giao đến trang Velocity. Bởi vì Confluence được xây
dựng dưa trên Sping Framework vì thế ta phải gọi một Action class để chuyển tiếp để trang velocity, tương tự
như gọi một controller trong spring. Acction class này sẽ kế thừa lớp ConfluenceActionSupport và override 
method excecute().

Để liên kiết Action class và Velocity template, ta cần phải thực hiện một số thao tác ở file atlassian-plugin.xml
Bằng cách thểm tag <xwork name="name" key="key">, ta sẽ kết nối action class và velocity.
<description>: miêu tả. Optional
<package> chi tiết về package. attribute:
					- name: ten của package
					- extends: default;
					- namespace: đường dẫn cho trang.
<action> chỉ ra acction class. attribute: 
					- name: ten của Action class.
					- class: chỉ ra tên của Acction class(kể cả package)
<result> chỉ ra kế quể trả về khi gọi tới Action class(trương hợp trong sample là trang Velocity). attribute:
					- name: kết quả trả về từ action class
					- type: chỉ ra loại là về velocity hoặc redirect.

<web-item> sẽ thêm đường link tới trang mà ta khai báo ở thẻ <xwork> và menu của confluence.

Sau khi hoàn thành tất cả các bước, ta sẽ sử dụng lệnh atlas-cli để đưa lên atlassian server.