import {loginUser} from "./Api.js";
import {LoginView, SignupView} from "./LoginView.js";


const form = document.querySelector("form")
const signupButton = document.querySelector("#signup")
const loginButton = document.querySelector("#login")


form.addEventListener('submit', (e) => {
    e.preventDefault()

    const formData = new FormData(form)

    const formDataObject = {}
    formData.forEach((value, key) => {
        formDataObject[key] = value;
    })

    loginUser(formDataObject).then(responseData => {
        document.cookie = `JWTTOKEN=${responseData.token}`
        window.location.href = "http://localhost:3000/main-page"
    })

})

signupButton.addEventListener("click", (e) => {
    e.preventDefault()
    form.innerHTML = SignupView
    document.querySelector("#forget-pw").hidden = true
    document.querySelector("#signup").hidden = true
    document.querySelector("#login").hidden = false
})

loginButton.addEventListener("click", (e) => {
    e.preventDefault()
    form.innerHTML = LoginView
    document.querySelector("#forget-pw").hidden = false
    document.querySelector("#signup").hidden = false
    document.querySelector("#login").hidden = true
})