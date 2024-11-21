
console.log("boardComment.js in!!");
console.log(bnoVal);

document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    let writer = document.getElementById('cmtWriter');
    let content = document.getElementById('cmtText');

    if(content.value == "" || content.value == null){
        alert("내용을 입력해주세요.");
        return false;
    }

    let cmtData = {
        bno : bnoVal,
        writer : writer.innerText,
        content : content.value
    }

    RegisterToServer(cmtData).then(result => {
        if(result == '1'){
            alert("댓글 등록 성공했습니다.");
            content.value = "";
            // 댓글 출력하기
            printCommArea(bnoVal);
        }else{
            alert("댓글 등록 실패했습니다.");
        }
    });

});


document.addEventListener('click', (e)=>{
    // 수정 버튼 - 1
    if(e.target.classList.contains("mod")){
        let cno = e.target.dataset.cno;
        let writer = e.target.dataset.writer;
        let content = e.target.dataset.content;

        document.getElementById('exampleModalLabel').innerText = writer;
        document.getElementById('cmtTextMod').value = content;
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);

    }
    // 삭제 버튼
    if(e.target.classList.contains("del")){
        let cno = e.target.dataset.cno;

        delCommToServer(cno).then(reault =>{
            if(reault == '1'){
                alert("댓글 삭제 성공!");
                printCommArea(bnoVal);
            }
        });
    }
});

// 수정 버튼 - 2
document.getElementById('cmtModBtn').addEventListener('click', ()=>{
    let cno = document.getElementById('cmtModBtn').dataset.cno;
    let writer = document.getElementById('exampleModalLabel').innerText;
    let content = document.getElementById('cmtTextMod').value;

    const cmtData = {
        cno : cno,
        writer : writer,
        content : content
    }

    modCommToServer(cmtData).then(result =>{
        if(result == '1'){
            alert("댓글 수정 성공!");
        }else{
            alert("댓글 수정 실패. 오류 발생!!");
        }
        document.querySelector('.btn-close').click();
        printCommArea(bnoVal);
    });
});

document.getElementById('moreBtn').addEventListener('click', ()=>{
    let page = parseInt(document.getElementById('moreBtn').dataset.page);
    printCommArea(bnoVal,page);
});

function printCommArea(bno, page = 0) {
    getCommFromServer(bno, page).then(result =>{
        const div = document.getElementById('cmtListArea');

        if(result.content.length>0){
            if(page == 0){
                div.innerHTML = "";
            }

            for(cvo of result.content){
                let str = `<div class="cmtBox"><p><strong>${cvo.writer}</strong> : ${cvo.content}</p>`;
                str += `<div class="btn-group me-2 btn-group-sm" role="group" aria-label="First group">`;
                str += `<button type="button" class="btn btn-outline-secondary mod" data-cno=${cvo.cno} data-writer=${cvo.writer} data-content=${cvo.content} data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                str += `<button type="button" class="btn btn-outline-secondary del" data-cno=${cvo.cno}>삭제</button></div>`;
                str += `</div>`;
                div.innerHTML += str;

            }

            let moreBtn = document.getElementById('moreBtn');

            if(result.totalPages-1 > result.number){
                moreBtn.style.visibility = 'visible'; 
                moreBtn.dataset.page = page + 1;  
            }else{
                moreBtn.style.visibility = 'hidden'; 
            }

        }
    });
}

async function RegisterToServer(cmtData) {
    try {
        const url = "/comment/register"
        const config = {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommFromServer(bno, page) {
    try {
        const url = "/comment/list/" + bno + "/" + page
        const resp = await fetch(url);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// (비동기) 댓글 삭제
async function delCommToServer(cno) {
    try {
        const url = "/comment/delete/" + cno;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// (비동기) 댓글 수정
async function modCommToServer(cmtData) {
    try {
        const url = "/comment/modify";
        const config = {
            method : 'put',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}