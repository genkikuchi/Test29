package nobworks.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface URLBeanFactory extends AutoBeanFactory {
	AutoBean<URLBeanInterface> getURLBean();
}
