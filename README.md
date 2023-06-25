1. UserNamePasswordAuthenticationToken là một lớp trong Spring Security được sử dụng để đại diện cho thông tin xác thực dựa trên tên người dùng và mật khẩu. Nó kế thừa từ lớp AbstractAuthenticationToken.

UserNamePasswordAuthenticationToken chứa thông tin về người dùng và mật khẩu được cung cấp bởi người dùng khi họ đăng nhập. Nó là một dạng của Authentication, một đối tượng chứa các thông tin xác thực được sử dụng bởi Spring Security để xác định danh tính của người dùng.

2. AuthenticationManager là một interface trong Spring Security được sử dụng để quản lý và xác thực các yêu cầu xác thực từ người dùng. Nó đại diện cho một thành phần quan trọng trong quá trình xác thực và ủy quyền trong Spring Security.

AuthenticationManager có nhiệm vụ nhận các đối tượng Authentication và thực hiện quá trình xác thực. Quá trình này bao gồm kiểm tra thông tin xác thực, xác minh tính hợp lệ của người dùng và cấp quyền truy cập tương ứng.

3. AuthenticationProvider là một interface trong Spring Security được sử dụng để cung cấp quá trình xác thực (authentication) cho một loại cụ thể của đối tượng Authentication. Nó đảm nhận trách nhiệm xác thực thông tin đăng nhập của người dùng và cung cấp các chi tiết về người dùng đã xác thực.

4. PasswordEncoder là một interface trong Spring Security được sử dụng để mã hóa (hash) mật khẩu người dùng. Nó giúp bảo mật mật khẩu bằng cách chuyển đổi mật khẩu thành một chuỗi mã hóa không thể đọc ngược được (one-way hashing).

5. UserDetailsService là một interface trong Spring Security được sử dụng để cung cấp thông tin về người dùng trong quá trình xác thực. Nó giúp tìm kiếm và trả về chi tiết người dùng dựa trên tên người dùng (username) trong quá trình xác thực.

UserDetails là một interface trong Spring Security đại diện cho thông tin chi tiết về người dùng. Nó chứa các thông tin cần thiết để xác thực và ủy quyền người dùng trong hệ thống.

7. Workflow xử lý trong Spring Security bao gồm các bước sau:

a, Gửi yêu cầu xác thực: Khi một yêu cầu bảo mật được gửi từ client (trình duyệt, ứng dụng di động, API,...), Spring Security sẽ bắt đầu quá trình xử lý bằng việc kiểm tra xem yêu cầu có yêu cầu xác thực hay không.

b, Xác thực người dùng: Nếu yêu cầu yêu cầu xác thực, Spring Security sẽ tìm kiếm thông tin người dùng tương ứng dựa trên thông tin đăng nhập được cung cấp (ví dụ: tên người dùng và mật khẩu). Để thực hiện việc này, nó sử dụng một UserDetailsService để tìm kiếm người dùng trong nguồn dữ liệu được cấu hình (ví dụ: cơ sở dữ liệu, lưu trữ bộ nhớ, LDAP).

c, Tạo đối tượng Authentication: Sau khi xác thực thành công, Spring Security sẽ tạo ra một đối tượng Authentication đại diện cho người dùng được xác thực. Đối tượng này chứa thông tin về người dùng và các quyền (authorities) mà người dùng có.

d, Lưu đối tượng Authentication: Đối tượng Authentication được lưu trữ trong một đối tượng SecurityContext, mà có thể được truy cập từ bất kỳ đâu trong ứng dụng. Điều này cho phép xác thực và ủy quyền trong các phần khác nhau của ứng dụng.

e, Xác thực ủy quyền: Sau khi xác thực, Spring Security sẽ xác định quyền truy cập mà người dùng được phép thực hiện trên các tài nguyên hoặc chức năng cụ thể. Điều này được thực hiện thông qua việc so khớp quyền được cấu hình và quyền được định nghĩa trong đối tượng Authentication.

f, Phân quyền và truy cập tài nguyên: Dựa trên quyền truy cập của người dùng, Spring Security sẽ quyết định xem người dùng có được phép truy cập vào tài nguyên hay không. Nếu người dùng không có quyền truy cập, ứng dụng sẽ trả về mã lỗi hoặc chuyển hướng đến trang lỗi tương ứng.

g, Hoàn tất quá trình xử lý: Khi quá trình xác thực và ủy quyền hoàn tất, Spring Security cho phép yêu cầu tiếp tục xử lý và trả về kết quả

8. Session là một khái niệm để lưu trữ và quản lý thông tin trạng thái của người dùng trong suốt phiên làm việc trên một trang web.

Khi một người dùng truy cập vào một trang web, một session mới sẽ được tạo ra trên phía server để lưu trữ thông tin liên quan đến người dùng. Session bao gồm các biến và giá trị được lưu trữ trong bộ nhớ trên server và được liên kết với người dùng thông qua một ID duy nhất. ID session thường được gửi đến trình duyệt của người dùng thông qua cookie hoặc tham số truy vấn.

9. Cookie là một đoạn dữ liệu nhỏ được lưu trữ trên trình duyệt của người dùng. Nó được sử dụng để lưu trữ thông tin về phiên làm việc, cài đặt ngôn ngữ, tuỳ chọn cá nhân và các dữ liệu khác liên quan đến trạng thái và ưu tiên của người dùng trên các trang web. Khi người dùng truy cập lại trang web, trình duyệt sẽ gửi cookie đến server để định danh và cung cấp thông tin cần thiết.

10. Sự khác biệt giữa session và cookie:

a, Session:

Lưu trữ trên phía server: Session được tạo và lưu trữ trên phía server. Mỗi session có một ID duy nhất được gửi đến trình duyệt của người dùng thông qua cookie hoặc tham số truy vấn.
Dữ liệu lưu trữ: Session có thể lưu trữ nhiều thông tin liên quan đến người dùng, bao gồm thông tin đăng nhập, giỏ hàng, trạng thái phiên làm việc và các dữ liệu khác.
Bảo mật: Thông tin trong session được lưu trữ trên server và không được truy cập trực tiếp từ phía client, điều này giúp bảo mật hơn so với cookie.
Thời gian sống: Session có thời gian sống giới hạn, được xác định bởi cấu hình của server. Khi thời gian sống session hết, session sẽ bị hủy và thông tin liên quan đến người dùng sẽ bị mất.

b, Cookie:

Lưu trữ trên phía client: Cookie được tạo ra và lưu trữ trên phía client, tức là trình duyệt của người dùng.
Dữ liệu lưu trữ: Cookie thường được sử dụng để lưu trữ thông tin nhỏ gọn như ID session, thông tin đăng nhập, tuỳ chọn ngôn ngữ, quyền truy cập và các dữ liệu nhỏ khác.
Bảo mật: Cookie được lưu trữ trên client và có thể bị truy cập hoặc chỉnh sửa bởi người dùng hoặc các công cụ khác. Để bảo mật thông tin quan trọng, thông tin nhạy cảm thường không được lưu trữ trong cookie.
Thời gian sống: Cookie có thể có thời gian sống giới hạn, được xác định khi tạo cookie. Một số cookie có thời gian sống chỉ trong phiên làm việc (session cookie), trong khi các cookie khác có thể tồn tại trong một khoảng thời gian dài (persistent cookie).