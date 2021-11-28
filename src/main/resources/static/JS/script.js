console.log("this is script file")
const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //true
    //band karna hai
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    //false
    //show karna hai
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

function deleteContact(contactId){
			
			swal({
				  title: "Are you sure?",
				  text: "you want to delete this contact..",
				  icon: "warning",
				  buttons: true,
				  dangerMode: true,
				})
				.then((willDelete) => {
				  if (willDelete) {
				   
					  
					  window.location="/user/deleteContact/"+contactId;
					  
				  } else {
				    swal("Your contact is safe !!");
				  }
				});
			
		}
//console.log("Hello, World!");
const searchFunc=()=>{
  console.log("Searching....")
  let query = $("#search-contact").val();//targeting search-contact id
 
  if(query==''){
    $(".search-result").hide();
  }
  else{
     console.log(query);//displaying query
     let url=`http://localhost:8080/search/${query}`;
     //fetch return type = promise is 
     fetch(url).then((response) => { 
     return response.json()})
     .then((data) => { 
       console.log(data);
       let text=`<div class='list-group'>`;
       data.forEach((contacts) => {
         text +=  `<a href='/user/contactList/showProfile/${contacts.contactId}' class='list-group-item list-group-action'>${contacts.name}</a>`
       });
       text += `</div>`;
       $(".search-result").html(text);
       $(".search-result").show();
     }).catch(e => {
    console.log(e);
});
     $(".search-result").show();
  }
}