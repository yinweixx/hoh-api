
$(document).ready(function(){
      $(".drop .slide").each(function(i,o){
         $(this).click(function(){
             if($(this).hasClass("fa-angle-double-down")){
                 $(this).stop().removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
             } else {
                 $(this).stop().removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
             }


         })

      })

})
