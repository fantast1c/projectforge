package org.projectforge.plugins.eed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.projectforge.export.AttrColumnDescription;

public enum ExtendedEmployeeDataEnum
{

  MOBILECONTRACT("plugins.eed.listcare.optionDropDown.costmobilecontract", Arrays
      .asList(new AttrColumnDescription("mobilecontract", "mobilecontract", "fibu.employee.mobilecontract.title"))), //
  MOBILECHECK("plugins.eed.listcare.optionDropDown.costmobiledevice",
      Arrays.asList(new AttrColumnDescription("mobilecheck", "mobilecheck", "fibu.employee.mobilecheck.title"))), //
  COSTTRAVEL("plugins.eed.listcare.optionDropDown.costtravel",
      Arrays.asList(new AttrColumnDescription("costtravel", "costtravel", "fibu.employee.costtravel.title"))), //
  EXPENSES("plugins.eed.listcare.optionDropDown.expenses",
      Arrays.asList(new AttrColumnDescription("expenses", "expenses", "fibu.employee.costtravel.title"))), //
  OVERTIME("plugins.eed.listcare.optionDropDown.overtime",
      Arrays.asList(new AttrColumnDescription("overtime", "overtime", "fibu.employee.overtime.title"))), //
  BONUS("plugins.eed.listcare.optionDropDown.bonus",
      Arrays.asList(new AttrColumnDescription("bonus", "bonus", "fibu.employee.bonus.title"))), //
  SPECIALPAYMENT("plugins.eed.listcare.optionDropDown.specialpayment", Arrays
      .asList(new AttrColumnDescription("specialpayment", "specialpayment", "fibu.employee.specialpayment.title"))), //
  TARGETAGREEMENTS("plugins.eed.listcare.optionDropDown.targetagreements", Arrays.asList(
      new AttrColumnDescription("targetagreements", "targetagreements", "fibu.employee.targetagreements.title"))), //
  COSTSHOP("plugins.eed.listcare.optionDropDown.costshop",
      Arrays.asList(new AttrColumnDescription("costshop", "costshop", "fibu.employee.costshop.title"))), //
  WEEKENDWORK("plugins.eed.listcare.optionDropDown.weekendwork",
      Arrays.asList(
          new AttrColumnDescription("weekendwork", "workinghourssaturday", "fibu.employee.weekendwork.saturday"),
          new AttrColumnDescription("weekendwork", "workinghourssunday", "fibu.employee.weekendwork.sunday"),
          new AttrColumnDescription("weekendwork", "workinghoursholiday", "fibu.employee.weekendwork.holiday"))), //
  OTHERS("plugins.eed.listcare.optionDropDown.others",
      Arrays.asList(new AttrColumnDescription("others", "others", "fibu.employee.others.title"))), //
  NONE("", Collections.emptyList()), //
  NOT_FOUND("", Collections.emptyList());

  private String i18nKeyDropDown;

  private List<AttrColumnDescription> attrColumnDescription;

  ExtendedEmployeeDataEnum(String i18nKeyDropDown, List<AttrColumnDescription> attrColumnDescription)
  {
    this.i18nKeyDropDown = i18nKeyDropDown;
    this.attrColumnDescription = attrColumnDescription;
  }

  public String getI18nKeyDropDown()
  {
    return i18nKeyDropDown;
  }

  public List<AttrColumnDescription> getAttrColumnDescription()
  {
    return attrColumnDescription;
  }

  public String getFirstAttrXMLGroupName()
  {
    for (AttrColumnDescription acd : this.attrColumnDescription) {
      return acd.getGroupName();
    }
    return null;
  }

  public static ExtendedEmployeeDataEnum findByAttrXMLKey(String attrXMLKey)
  {
    if (attrXMLKey == null) {
      return NONE;
    }
    for (ExtendedEmployeeDataEnum so : ExtendedEmployeeDataEnum.defaultValues()) {
      if (so.getFirstAttrXMLGroupName().equals(attrXMLKey)) {
        return so;
      }
    }
    return NOT_FOUND;
  }

  public static List<AttrColumnDescription> getAllAttrColumnDescriptions()
  {
    List<AttrColumnDescription> resultList = new ArrayList<>();
    for (ExtendedEmployeeDataEnum eede : defaultValues()) {
      resultList.addAll(eede.getAttrColumnDescription());
    }
    return resultList;
  }

  public static List<ExtendedEmployeeDataEnum> defaultValues()
  {
    return Stream.of(ExtendedEmployeeDataEnum.values())
        .filter(so -> so.equals(ExtendedEmployeeDataEnum.NONE) == false
            && so.equals(ExtendedEmployeeDataEnum.NOT_FOUND) == false)
        .collect(Collectors.toList());
  }

}