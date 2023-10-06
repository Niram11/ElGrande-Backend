import {loginUser} from "./Api.js";


const form = document.querySelector("form")

form.addEventListener('submit', (e) => {
    e.preventDefault()

    const formData = new FormData(form)

    const formDataObject = {}
    formData.forEach((value, key) => {
        formDataObject[key] = value;
    })

    loginUser(formDataObject)
})