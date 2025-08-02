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

                const token = result.data;

                if (token) {
                    localStorage.setItem('jwtToken', token);
                    await Swal.fire({
                        icon: 'success',
                        title: 'Sign In Successful!',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    window.location.href = 'dashboard.html';
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Authentication Failed',
                        text: 'Token not found.',
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
});
