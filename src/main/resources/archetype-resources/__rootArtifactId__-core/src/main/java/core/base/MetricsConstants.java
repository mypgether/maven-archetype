package ${package}.core.base;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;

/**
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class MetricsConstants {

  public static Counter commonCount;
  public static Histogram commonTimer;
  public static Histogram reqTimer;

  static {
    commonCount = Counter.build().namespace("${rootArtifactId}").name("common_count").help("Common count.")
        .labelNames("function", "type").register();
    commonTimer = Histogram.build().namespace("${rootArtifactId}").name("common_time").help("Common time.")
        .labelNames("function").register();
    reqTimer = Histogram.build().namespace("${rootArtifactId}").name("req_time").help("Request time.")
        .labelNames("path").register();
  }
}