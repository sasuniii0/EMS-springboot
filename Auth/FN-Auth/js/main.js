const BASE_URL = 'http://localhost:8080/auth';

document.addEventListener('DOMContentLoaded', () => {
    const path = window.location.pathname;

    // Handle Sign Up
    if (path.includes('signup.html')) {
        const signupForm = document.querySelector('form');
        signupForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const userDTO = {
                name : document.getElementById('name').value.trim(),
                username: document.getElementById('username').value.trim(),
                email: document.getElementById('email').value.trim(),
                password: document.getElementById('password').value.trim()
            };

            try {
                const response = await fetch(`${BASE_URL}/signup`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(userDTO)
                });

                const result = await response.json();
                console.log('Sign Up response:', result);

                if (response.ok) {
                    await Swal.fire({
                        icon: 'success',
                        title: 'Sign Up Successful!',
                        text: 'You can now sign in.',
                        confirmButtonColor: '#3085d6'
                    });
                    window.location.href = 'index.html';
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Sign Up Failed',
                        text: result.message || 'Unknown error',
                        confirmButtonColor: '#d33'
                    });
                }
            } catch (err) {
                console.error(err);
                Swal.fire({
                    icon: 'error',
                    title: 'Oops!',
                    text: 'Error during sign up.',
                    confirmButtonColor: '#d33'
                });
            }
        });
    }

    // Handle Sign In
    if (path.includes('index.html') || path.endsWith('/')) {
        const signingForm = document.querySelector('form');
        signingForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const authDTO = {
                username: document.getElementById('username').value.trim(),
                password: document.getElementById('password').value.trim()
            };

            try {
                const response = await fetch(`${BASE_URL}/signing`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(authDTO)
                });

                const result = await response.json();
                console.log('Login response:', result);

                const data = result.data;
                const token = result.accessToken;
                const username = data.username;
                const role = result.role;

                console.log("Token:", token);
                console.log("Role:", role);
                console.log("Username:", username);

                sessionStorage.setItem('jwtToken', token);
                sessionStorage.setItem('userRole', role);

                if (data && data.username && data.role){
                    document.cookie = `jwtToken=${data.accessToken}; path=/; max-age=3600`;
                    sessionStorage.setItem('userRole', data.role);
                    window.location.href = "dashboard.html";
                    Swal.fire({
                        icon: 'success',
                        title: 'Sign In Successful!',
                        text: 'Welcome back!',
                        confirmButtonColor: '#3085d6'
                    }).then(() => {
                        window.location.href = 'dashboard.html';
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Authentication Failed',
                        text: 'Token or role not found',
                        confirmButtonColor: '#d33'
                    });
                }
            } catch (err) {
                console.error(err);
                Swal.fire({
                    icon: 'error',
                    title: 'Oops!',
                    text: 'Error during sign in.',
                    confirmButtonColor: '#d33'
                });
            }
        });
    }

    if (path.includes('dashboard.html') || path.endsWith('/')) {
        const role = sessionStorage.getItem('userRole');
        console.log('Role from sessionStorage:', role);
        const jwt = document.cookie.split('; ').find(row => row.startsWith('jwtToken='));
        const token = jwt ? jwt.split('=')[1] : null;

        console.log(
            'Dashboard loaded with role:', role, 'and token:', token
        )

        if (!role || !token){
            Swal.fire({
                icon: 'warning',
                title: 'Unauthorized',
                text: 'You are not authorized to view this page. Please log in.',
                confirmButtonColor: '#3085d6'
            }).then(() => {
                sessionStorage.clear();
                window.location.href = 'index.html';
            });
            return;
        }
        document.getElementById('user-name').innerText = role;

        let endpoint = '';
        if (role === 'ADMIN' || role === 'admin') {
            endpoint = 'http://localhost:8080/hello/admin';
        }else if (role === 'USER' || role === 'user') {
            endpoint = 'http://localhost:8080/hello/user';
        } else {
            alert('Invalid role. Logging out.');
            sessionStorage.clear();
            window.location.href = 'index.html';
            return;
        }

        fetch(endpoint,{
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(res=>{
            if (!res.ok) throw new Error('Access denied');
            return res.text();
        }).catch(err=>{
            console.error(err);
            alert('Error fetching dashboard info. Logging out.');
            sessionStorage.clear();
            window.location.href = 'index.html';
        })
    }
});
