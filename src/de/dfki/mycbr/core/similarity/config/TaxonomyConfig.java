/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r
 * K&uuml;nstliche Intelligenz DFKI GmbH.
 * All rights reserved.
 *
 * myCBR is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Since myCBR uses some modules, you should be aware of their licenses for
 * which you should have received a copy along with this program, too.
 *
 * endOfLic */

package de.dfki.mycbr.core.similarity.config;

/**
 * Enumerates the configuration for the semantics of inner nodes
 * as query or case values.
 *
 * @author myCBR Team
 *
 */
public enum TaxonomyConfig {
    /**
     *
     */
    INNER_NODES_ANY, INNER_NODES_PESSIMISTIC, INNER_NODES_OPTIMISTIC,
    /**
     *
     */
    INNER_NODES_AVERAGE, NO_INNERNODES;
}
