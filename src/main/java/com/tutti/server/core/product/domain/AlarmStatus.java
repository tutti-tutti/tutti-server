package com.tutti.server.core.product.domain;

import lombok.Getter;

@Getter
public enum AlarmStatus {

  WAITING,        // 알림 대기중
  NOTIFIED,       // 알림 발송 완료
  CANCELLED       // 알림 취소

}
