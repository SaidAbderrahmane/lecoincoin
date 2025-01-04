<!DOCTYPE html>
<html lang="en">

<head>
    <%-- <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet"> --%>
        <!-- <meta name="viewport" content="width=device-width, initial-scale=1" /> -->
        <%-- <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css"> --%>
            <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
            <script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
            <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

            <title>Sign in</title>
</head>

<body class="h-screen w-screen">
    <section style="background-image: url('${resource(dir: 'images', file: 'login-bg.png')}');
            background-size: contain;
            background-position: center top; /* Focus on the top of the image */
            background-repeat: no-repeat;"
        class="w-screen h-screen flex items-center justify-center">
        <div class="flex flex-col items-center justify-center">
            <a href="/" class="flex items-center space-x-3 rtl:space-x-reverse">
                <asset:image src="lecoincoin-logo.png" width="150" alt="lecoincoin Logo" />
            </a>
            <div class="w-full bg-white rounded-lg shadow dark:border xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div class="p-4 space-y-4 sm:p-6 w-96">
                    <h1 class="text-lg font-bold leading-tight tracking-tight text-gray-900 md:text-xl dark:text-white">
                        Sign in to your account
                    </h1>
                    <form class="space-y-4" action="${postUrl ?: '/login/authenticate'}" method="POST" id="loginForm"
                        autocomplete="off">
                        <div>
                            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your
                                email</label>
                            <input name="${usernameParameter ?: 'username'}" id="username" placeholder="Username"
                                required
                                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                        </div>
                        <div>
                            <label for="password"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                            <input type="password" name="${passwordParameter ?: 'password'}" id="password"
                                placeholder="••••••••"
                                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                required>
                        </div>
                        <input type="submit" value="${message(code: 'springSecurity.login.button')}"
                            class="text-white w-full bg-orange-500 hover:bg-orange-800 focus:ring-4 focus:ring-orange-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">
                    </form>

                    <div class="message">
                        <g:if test='${flash.message}'>
                            <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
                                role="alert">
                                ${flash.message}
                            </div>

                        </g:if>
                    </div>

                </div>
            </div>
        </div>
    </section>
</body>

</html>