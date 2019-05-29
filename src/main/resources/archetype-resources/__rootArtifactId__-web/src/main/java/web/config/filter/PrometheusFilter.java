package ${package}.web.config.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import ${package}.core.base.MetricsConstants;
import io.prometheus.client.Histogram.Timer;
import lombok.extern.slf4j.Slf4j;

/**
 * dubbo中的promethues监控
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Slf4j
@Activate(order = 10000)
public class PrometheusFilter implements Filter {

  public PrometheusFilter() {
  }

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    if (!invocation.getMethodName().startsWith("$")) {
      String path = invocation.getMethodName();
      Timer timer = MetricsConstants.reqTimer.labels(path).startTimer();
      try {
        return invoker.invoke(invocation);
      } finally {
        timer.observeDuration();
      }
    }
    return invoker.invoke(invocation);
  }
}