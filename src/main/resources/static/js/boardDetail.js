
console.log("boardDetail.js in!!");

document.getElementById('listBtn').addEventListener('click', ()=>{
    location.href = "/board/list";
});

document.getElementById('modBtn').addEventListener('click', ()=>{
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;

    document.getElementById('h3').innerText = "Board Modify Page!!";

    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    let modBtn = document.createElement('button');
    modBtn.setAttribute("type","submit");
    modBtn.classList.add("btn", "btn-secondary", "btn-sm");
    modBtn.innerText = "저장";

    document.getElementById("modForm").appendChild(modBtn);
});