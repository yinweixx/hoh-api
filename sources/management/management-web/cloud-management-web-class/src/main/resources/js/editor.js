
$(document).ready(function(){
    $(".col-md-2 .pen").each(function(i,o){
        $(this).click(function() {

            var parent  = $(this).parent().parent();

            $(".ng-scope",parent).addClass("none");
            $(".form-group",parent).removeClass("none").addClass("block");
            $(".form-group input",parent).focus();
            $(".editor").eq(i).css("margin-right", "200px");
            $(this).addClass("none");
        })

        $(".form-group input").blur(function(){

            var parent  = $(this).parent().parent();

            $(".ng-scope",parent).removeClass("none");
            $(".form-group",parent).removeClass("block").addClass("none");
            $(".editor").css("margin-right", "0px");

            $(".pen",$(this).parents('.editor')).removeClass("none");
        })
    })

    $(".col-md-5 .pen").each(function(i,o){
        $(this).click(function() {

            var parent  = $(this).parent().parent();

            $(".ng-scope",parent).addClass("none");
            $(".form-group",parent).removeClass("none").addClass("block");
            $(".form-group input",parent).focus();
            $(".editor").eq(i).css("margin-right", "200px");
            $(this).addClass("none");
        })

        $(".form-group input").blur(function(){

            var parent  = $(this).parent().parent();

            $(".ng-scope",parent).removeClass("none");
            $(".form-group",parent).removeClass("block").addClass("none");
            $(".editor").css("margin-right", "0px");

            $(".pen",$(this).parents('.editor')).removeClass("none");
        })
    })

})

