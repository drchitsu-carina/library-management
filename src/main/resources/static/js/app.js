document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("imageInput");
    const preview = document.getElementById("preview");

    if (input) {
        input.addEventListener("change", function () {
            const file = this.files[0];

            if (file) {
                preview.src = URL.createObjectURL(file);
                preview.classList.remove("d-none");
            }
        });
    }
});