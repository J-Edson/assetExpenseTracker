<ul class="nav flex-column text-center sidebar pt-3">
  <% def sidebarItems = ["Home", "Asset", "Expense", "Log"] %>
  <g:each var="sidebarItem" in="${sidebarItems}">
      <g:render template="components/sidebarItem" model="${['sidebarItem': sidebarItem, 'sidebarItems': sidebarItems]}"/>
  </g:each>
</ul>
