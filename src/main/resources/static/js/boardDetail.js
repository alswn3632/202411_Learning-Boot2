
console.log("boardDetail.js in!!");

document.getElementById('listBtn').addEventListener('click', ()=>{
    location.href = "/board/list";
});

document.getElementById('delBtn').addEventListener('click', ()=>{
    location.href = "/board/delete?bno=" + bnoVal;
});

document.getElementById('modBtn').addEventListener('click', ()=>{
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;

    document.getElementById('h3').innerText = "Board Modify Page!!";

    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    let modBtn = document.createElement('button');
    modBtn.setAttribute("type","submit");
    modBtn.setAttribute("id", "regBtn");
    modBtn.classList.add("btn", "btn-secondary", "btn-sm");
    modBtn.innerText = "저장";

    document.getElementById("modForm").appendChild(modBtn);

    let fileDelBtn = document.querySelectorAll(".file-x");
    for(let delBtn of fileDelBtn){
        delBtn.disabled = false;
    }

    document.querySelector(".file-up").disabled = false;

});

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid
        deleteFileToServer(uuid).then(result  =>{
            if(result == '1'){
                console.log("파일 삭제 성공");
                e.target.closest('div').remove();

            }else{
                alert("삭제를 진행할 수 없습니다.");
            }
        });
    }
});

async function deleteFileToServer(uuid) {
    try {
        const url = "/board/file/" + uuid;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error)
    }
}