import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/canteen-user">
        <Translate contentKey="global.menu.entities.canteenUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/school">
        <Translate contentKey="global.menu.entities.school" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-category">
        <Translate contentKey="global.menu.entities.productCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/menu">
        <Translate contentKey="global.menu.entities.menu" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification-history">
        <Translate contentKey="global.menu.entities.notificationHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/activation-code">
        <Translate contentKey="global.menu.entities.activationCode" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-account">
        <Translate contentKey="global.menu.entities.userAccount" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/transaction">
        <Translate contentKey="global.menu.entities.transaction" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-order">
        <Translate contentKey="global.menu.entities.userOrder" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
