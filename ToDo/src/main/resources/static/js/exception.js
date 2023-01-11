const dino = document.getElementById("dino");
const cactus = document.getElementById("cactus");
let ok=false;
function jump() {
    if (dino.classList != "jump") {
        dino.classList.add("jump");
        setTimeout(function () {
            dino.classList.remove("jump");
        }, 300);
    }
}

let isAlive = setInterval(function () {
    if(ok==true) {
        let dinoTop = parseInt(window.getComputedStyle(dino).getPropertyValue("top"));
        let cactusLeft = parseInt(
            window.getComputedStyle(cactus).getPropertyValue("left")
        );

        if (cactusLeft < 40 && cactusLeft > 0 && dinoTop >= 140) {
            alert("Game Over!");
            ok=false;
            cactus.classList.remove("animation");
        }
    }
}, 10);

document.addEventListener("keydown", function (event) {
    if(ok==true)
    {
        jump();
    }
    else
    {
        if(window.innerWidth>=800)
        {
            ok=true;
            cactus.classList.add("animation");
        }
    }
});