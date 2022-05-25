let CHECK_STATUS=false;
let LOGIN_ID_STATUS=false;
let NICKNAME_ID_STATUS=false;






async function checkDupleLoginId(){

    let inputLoginId= document.querySelector("#loginId")
    let loginId=inputLoginId.value;

    await fetch("http://localhost:8085/members/check/loginId?loginId=" +encodeURI(loginId))
    .then(

        (response)=> {
            return response.json();
        }

    )
    .then(

        (data) =>{
            let idCheck=data;


            console.log(idCheck.status);

            if(idCheck.status){
                LOGIN_ID_STATUS=false;
                console.log(LOGIN_ID_STATUS);
                alert("이미 존재하는 아이디 입니다.");
            }
            else if(loginId===""){
                LOGIN_ID_STATUS=false;
                console.log(LOGIN_ID_STATUS);
                alert("입력하신 아이디가 공백상태 입니다.");
            }

            else{
                LOGIN_ID_STATUS=true;
                console.log(LOGIN_ID_STATUS);
                alert("가입할 수 있는 아이디 입니다.");
            }



        }

    )
    .catch(
        (error) =>{
            console.log(error);
        }
    )

}

async function checkDupleNickName(){

        let inputNickName= document.querySelector("#nickName")
        let nickName=inputNickName.value;

        await fetch("http://localhost:8085/members/check/nickName?nickName=" +encodeURI(nickName))
        .then(

            (response)=> {
                return response.json();
            }

        )
        .then(

            (data) =>{
                let nickNameCheck=data;


                console.log(nickNameCheck.status);


                    if(nickNameCheck.status){
                        NICKNAME_ID_STATUS=false;
                        console.log(NICKNAME_ID_STATUS);
                        alert("이미 존재하는 닉네임 입니다.");
                    }
                    else if(nickName===""){
                        NICKNAME_ID_STATUS=false;
                        console.log(NICKNAME_ID_STATUS);
                        alert("입력하신 닉네임이 공백상태 입니다.");
                    }

                    else{
                        NICKNAME_ID_STATUS=true;
                        console.log(NICKNAME_ID_STATUS);
                        alert("사용할 수 있는 닉네임 입니다.");
                    }


            }

        )
        .catch(
            (error) =>{
                console.log(error);
            }
        )


}




function checkStatus(){




    if(LOGIN_ID_STATUS&&NICKNAME_ID_STATUS){

       CHECK_STATUS=true;

    }
    else{
        CHECK_STATUS=false;
    }

    if(!CHECK_STATUS){

        alert("중복확인을 해주시기 바랍니다.");
        return false;
    }

}