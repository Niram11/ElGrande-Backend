export const loginUser = (data) => {
    fetch("http://localhost:8080/api/v1/auths/jwt/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        redirect: "follow",
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status !== 201) {
                throw new Error("Response was not created")
            }
            return response.json()
        })
        .then(responseData => {
            document.cookie = `JWTTOKEN=${responseData.token}`
        })
        .catch(error => {
            console.error("Error fetching data: ", error)
            throw error
        })
}