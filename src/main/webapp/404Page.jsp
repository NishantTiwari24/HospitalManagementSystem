<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>404 - Not Found</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f2f4f7;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            color: #333;
        }

        .container {
            background-color: #fff;
            padding: 50px 40px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
            max-width: 600px;
            width: 100%;
            text-align: center;
        }

        .code {
            font-size: 90px;
            font-weight: bold;
            color: #e63946;
            margin-bottom: 10px;
        }

        .message {
            font-size: 24px;
            margin-bottom: 10px;
        }

        .detail {
            font-size: 15px;
            color: #6c757d;
            margin-bottom: 30px;
            white-space: pre-wrap;
        }

        .home-link {
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            padding: 12px 24px;
            border-radius: 8px;
            font-size: 15px;
            display: inline-block;
            transition: background-color 0.3s ease;
        }

        .home-link:hover {
            background-color: #0056b3;
        }

        @media (max-width: 480px) {
            .code {
                font-size: 60px;
            }

            .message {
                font-size: 20px;
            }

            .detail {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="code">Oops!</div>
        <div class="message">Something went wrong</div>
        <div class="detail">
            <% if (exception != null) { %>
                <%= exception.getMessage() %>
            <% } %>
        </div>
        <a href="<%= request.getContextPath() %>/home.jsp" class="home-link">Go to Homepage</a>
    </div>
</body>
</html>
