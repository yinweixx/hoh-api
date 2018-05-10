
$(document).ready(function() {
    $(".angel").click(function () {
        if ($(".list-unstyled").attr("class").indexOf("block") >= 0) {
            $(".list-unstyled").removeClass("block").addClass("none");
        } else {
            $(".list-unstyled").removeClass("none").addClass("block");
        }

    })


    $(".checkbox_item .ck").each(function (i, o) {
        $(this).click(function () {
            if($(this).attr("class").indexOf("background") >= 0){
                $(this).removeClass("background").addClass("white");
            }else{
                $(this).addClass("background").removeClass("white");
            }
        })

    })

    $(".current").click(function(){
        if($(".current").hasClass("background")){
            $(".current_child").removeClass("background2").removeClass("white").addClass("background");
            $(".tick",".child").removeClass("border-color");
        }else{
            $(".current_child").removeClass("background").addClass("white");
            $(".child").css("color","#CACBCC");
            $(".tick",".child").addClass("border-color");
            $(".current_child",".child").addClass("background2");
        }

    })



})






