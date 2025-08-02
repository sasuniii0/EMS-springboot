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
            console.log(userDTO)

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
                    alert('Sign Up successful! You can now sign in.');
                    window.location.href = 'index.html';
                } else {
                    alert(`Sign Up failed: ${result.message || 'Unknown error'}`);
                }
            } catch (err) {
                console.error(err);
                alert('Error during sign up.');
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
                console.log('Login response:', result); // ✅ For debugging

                const token = result.data;

                if (token) {
                    localStorage.setItem('jwtToken', token);
                    alert('Sign In successful!');
                    window.location.href = 'dashboard.html';
                } else {
                    alert('Authentication failed: Token not found.');
                }
            } catch (err) {
                console.error(err);
                alert('Error during sign in.');
            }
        });
    }
});
