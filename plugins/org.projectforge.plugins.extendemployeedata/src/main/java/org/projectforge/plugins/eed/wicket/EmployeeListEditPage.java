package org.projectforge.plugins.eed.wicket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.projectforge.business.fibu.EmployeeDO;
import org.projectforge.business.fibu.EmployeeTimedDO;
import org.projectforge.business.fibu.api.EmployeeService;
import org.projectforge.export.AttrColumnDescription;
import org.projectforge.export.DOListExcelExporter;
import org.projectforge.export.DOWithAttrListExcelExporter;
import org.projectforge.web.core.MenuBarPanel;
import org.projectforge.web.wicket.AbstractListPage;
import org.projectforge.web.wicket.CellItemListener;
import org.projectforge.web.wicket.CellItemListenerPropertyColumn;
import org.projectforge.web.wicket.IListPageColumnsCreator;
import org.projectforge.web.wicket.InputCellItemListenerPropertyColumn;

import de.micromata.genome.db.jpa.tabattr.api.TimeableService;

//@ListPage(editPage = EmployeeEditPage.class)
public class EmployeeListEditPage extends AbstractListPage<EmployeeListEditForm, EmployeeService, EmployeeDO> implements
    IListPageColumnsCreator<EmployeeDO>
{
  @SpringBean
  private EmployeeService employeeService;

  @SpringBean
  private TimeableService<Integer, EmployeeTimedDO> timeableService;

  public EmployeeListEditPage(final PageParameters parameters)
  {
    super(parameters, "fibu.employee");
  }

  @Override
  @SuppressWarnings("serial")
  public List<IColumn<EmployeeDO, String>> createColumns(final WebPage returnToPage, final boolean sortable)
  {
    final List<IColumn<EmployeeDO, String>> columns = new ArrayList<>();

    final CellItemListener<EmployeeDO> cellItemListener = (CellItemListener<EmployeeDO>) (item, componentId,
        rowModel) -> {
      final EmployeeDO employee = rowModel.getObject();
      appendCssClasses(item, employee.getId(), employee.isDeleted());
    };

    columns.add(new CellItemListenerPropertyColumn<>(new ResourceModel("name"),
        getSortable("user.lastname", sortable),
        "user.lastname", cellItemListener));

    columns.add(new CellItemListenerPropertyColumn<>(new ResourceModel("firstName"),
        getSortable("user.firstname", sortable),
        "user.firstname", cellItemListener));

    columns.add(new InputCellItemListenerPropertyColumn<>(new ResourceModel("firstName"),
        getSortable("user.firstname", sortable),
        "user.firstname", cellItemListener));

    return columns;
  }

  @Override
  protected DOListExcelExporter createExcelExporter(final String filenameIdentifier)
  {
    final String[] fieldsToExport = { "user", "kost1" };

    final AttrColumnDescription[] attrFieldsToExport = {
        new AttrColumnDescription("mobilecheck", "mobilecheck"),
        new AttrColumnDescription("ebikeleasing", "ebikeleasing")
    };
    
    final Date dateToSelectAttrRow = new Date(); // TODO CT: get date from web interface
    return new DOWithAttrListExcelExporter<>(filenameIdentifier, timeableService, fieldsToExport, attrFieldsToExport, dateToSelectAttrRow);
  }

  @Override
  protected void init()
  {
    final List<IColumn<EmployeeDO, String>> columns = createColumns(this, true);
    dataTable = createDataTable(columns, "user.lastname", SortOrder.ASCENDING);
    form.add(dataTable);

    // remove add and reindex buttons from context menu
    contentMenuBarPanel = new MenuBarPanel("menuBar");
    addExcelExport(getString("fibu.employee.title.heading"), "employees");
  }

  @Override
  protected EmployeeListEditForm newListForm(final AbstractListPage<?, ?, ?> parentPage)
  {
    return new EmployeeListEditForm(this);
  }

  @Override
  public EmployeeService getBaseDao()
  {
    return employeeService;
  }

  @Override
  protected void addBottomPanel(final String id)
  {
    form.add(form.getSaveButtonPanel(id));
  }

}