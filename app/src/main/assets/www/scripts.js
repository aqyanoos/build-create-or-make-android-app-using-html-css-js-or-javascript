
const socialMediaListEl = document.getElementById("social-media-links")



try {
    const links = Android.SocialMediaLinks()
    const linksArray = links.split(";")
    linksArray.forEach(link => {
        const liEl = document.createElement("li")
        const linkItems = link.split("&")
        liEl.innerHTML = "<a href='" + linkItems[0] + "'> " + linkItems[1] + "</a>"
        socialMediaListEl.appendChild(liEl)
    });
} catch (e) {
    console.log("Could not get the links: ", e)
    socialMediaListEl.innerHTML = "Could not get the links"
}