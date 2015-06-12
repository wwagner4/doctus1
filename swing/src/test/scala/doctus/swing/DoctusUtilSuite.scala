package doctus.swing

import org.apache.commons.lang.SystemUtils
import utest._

class DoctusUtilSuite extends TestSuite {

  def tests = TestSuite {

    "Check operating system name"-{
      if (SystemUtils.IS_OS_MAC) assert(DoctusSwingUtil.osName == Mac)
      else if (SystemUtils.IS_OS_LINUX) assert(DoctusSwingUtil.osName == Linux)
      else if (SystemUtils.IS_OS_WINDOWS) assert(DoctusSwingUtil.osName == Win)
      else assert(DoctusSwingUtil.osName == Unknown)
    }
  }

}