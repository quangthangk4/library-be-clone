package com.library.shared.templates;

import lombok.Getter;

@Getter
public enum EmailTemplates {
  VERIFY_EMAIL_TEMPLATE(
      "[Thư viện HCMUT] Xác nhận địa chỉ email của bạn",
      """
          <html>
          <head>
              <style>
                  body {
                      font-family: Arial, sans-serif;
                      background-color: #f4f4f4;
                      padding: 20px;
                  }
                  .container {
                      max-width: 600px;
                      margin: 0 auto;
                      background-color: #ffffff;
                      padding: 30px;
                      border-radius: 10px;
                      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                      text-align: center;
                  }
                  h1 {
                      color: #333333;
                  }
                  p {
                      font-size: 16px;
                      color: #555555;
                  }
                  .verify-button {
                      display: inline-block;
                      margin-top: 20px;
                      padding: 12px 24px;
                      background-color: #007bff;
                      color: #ffffff !important;
                      text-decoration: none;
                      font-weight: bold;
                      border-radius: 5px;
                      font-size: 16px;
                  }
                  .verify-button:hover {
                      background-color: #0056b3;
                  }
              </style>
          </head>
          <body>
              <div class="container">
                  <p>Xin chào <b>%s</b>,</p>
                  <p>Chào mừng bạn đến với hệ thống Thư viện Trực tuyến HCMUT.</p>
                  <p>Để hoàn tất đăng ký và kích hoạt tài khoản, vui lòng xác nhận địa chỉ email bằng cách nhấn vào nút bên dưới:</p>
                  <a class="verify-button" href="%s">Xác nhận email</a>
                  <p style="margin-top: 20px; font-size: 14px; color: #888888;">
                  Nếu nút trên không hoạt động, bạn có thể copy và dán đường dẫn sau vào trình duyệt:
                  <br/>
                  <a href="%s" style="color: #007bff; word-break: break-all;">%s</a>
                  </p>
                  <p style="font-size: 14px;">
                  Đường dẫn có hiệu lực trong <b>24 giờ</b> kể từ khi nhận được email này.
                  </p>
                  <hr style="border: none; border-top: 1px solid #eeeeee; margin: 20px 0;" />
                  <p style="font-size: 12px; color: #aaaaaa;">
                  Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email hoặc liên hệ bộ phận hỗ trợ nếu cần thiết.
                  </p>
              </div>
          </body>
          </html>
          """
  ),
  VERIFY_RESET_PASSWORD_TEMPLATE(
      "Xác nhận lấy lại mật khẩu và mở khóa tài khoản của bạn",
      """
          <html>
          <head>
              <style>
                  body {
                      font-family: Arial, sans-serif;
                      background-color: #f4f4f4;
                      padding: 20px;
                  }
                  .container {
                      max-width: 600px;
                      margin: 0 auto;
                      background-color: #ffffff;
                      padding: 30px;
                      border-radius: 10px;
                      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                      text-align: center;
                  }
                  h1 {
                      color: #333333;
                  }
                  p {
                      font-size: 16px;
                      color: #555555;
                  }
                  .verify-button {
                      display: inline-block;
                      margin-top: 20px;
                      padding: 12px 24px;
                      background-color: #007bff;
                      color: #ffffff !important;
                      text-decoration: none;
                      font-weight: bold;
                      border-radius: 5px;
                      font-size: 16px;
                  }
                  .verify-button:hover {
                      background-color: #0056b3;
                      color: #ffffff;
                  }
              </style>
          </head>
          <body>
              <div class="container">
                 <p>Xin chào <b>%s</b>,</p>
                 <p>Bạn đã yêu cầu đặt lại mật khẩu. Nhấn nút bên dưới để tiếp tục:</p>
                 <a class="verify-button" href="%s">Đặt lại mật khẩu</a>
                 <p style="margin-top: 15px;">
                 Hoặc copy link sau vào trình duyệt:
                 <br/>
                 <a href="%s">%s</a>
                 </p>
                 <p style="font-size: 14px;">
                 Link có hiệu lực trong <b>30 phút</b>.
                 </p>
                 <p style="font-size: 14px; color: #b00020;">
                 Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email hoặc kiểm tra bảo mật tài khoản.
                 </p>
              </div>
          </body>
          </html>
          """
  );

  private final String subject;
  private final String content;


  EmailTemplates(String subject, String content) {
    this.subject = subject;
    this.content = content;
  }

  public String formatContent(Object... args) {
    return String.format(this.content, args);
  }
}


