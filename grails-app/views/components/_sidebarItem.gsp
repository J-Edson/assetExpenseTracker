  <li class="nav-item" >
    <div class="sidebar-items" id="${sidebarItem}-item">
      <div class="row align-items-center p-3">
        <div class="col text-right">
            <% 
              def srcPath =  sidebarItem +"_icon.svg" 
              def srcPath1 =  sidebarItem +"_icon1.svg"
            %>

          <img id="${sidebarItem}Icon" src='${assetPath(src: srcPath)}' class="sidebar_icon"/>
        </div>
        <div class="col text-left">
          <div id="${sidebarItem}Sub">${sidebarItem}</div>
        </div> 
      </div>
    </div>
  </li>

<script>
    let ${sidebarItem}Item = document.getElementById(`${sidebarItem}-item`);
    let ${sidebarItem}Icon = document.getElementById(`${sidebarItem}Icon`);
    let ${sidebarItem}Sub = document.getElementById(`${sidebarItem}Sub`);
    
    ${sidebarItem}Item.addEventListener('mouseover', function() {
        ${sidebarItem}Icon.src=`${assetPath(src: srcPath1)}`
        ${sidebarItem}Sub.style.color='#2D60FF'
        ${sidebarItem}Item.style.borderLeft='5px solid #2D60FF';
    });
    ${sidebarItem}Item.addEventListener('mouseout', function() {
        if(!sidebarClick${sidebarItem}){
          ${sidebarItem}Icon.src=`${assetPath(src: srcPath)}`
          ${sidebarItem}Sub.style.color='#B1B1B1'
          ${sidebarItem}Item.style.borderLeft='';
        }
    });

    ${sidebarItem}Item.addEventListener('click', function() {
        sidebarClickHome = false
        sidebarClickAsset = false
        sidebarClickExpense = false
        sidebarClickLog = false
        sidebarClick${sidebarItem}=true

        HomeIcon.src=`${assetPath(src: 'Home_icon.svg')}`
        HomeSub.style.color='#B1B1B1'
        HomeItem.style.borderLeft='';

        AssetIcon.src=`${assetPath(src: 'Asset_icon.svg')}`
        AssetSub.style.color='#B1B1B1'
        AssetItem.style.borderLeft='';

        ExpenseIcon.src=`${assetPath(src: 'Expense_icon.svg')}`
        ExpenseSub.style.color='#B1B1B1'
        ExpenseItem.style.borderLeft='';

        LogIcon.src=`${assetPath(src: 'Log_icon.svg')}`
        LogSub.style.color='#B1B1B1'
        LogItem.style.borderLeft='';

        ${sidebarItem}Icon.src=`${assetPath(src: srcPath1)}`
        ${sidebarItem}Sub.style.color='#2D60FF'
        ${sidebarItem}Item.style.borderLeft='5px solid #2D60FF';

    });
</script>